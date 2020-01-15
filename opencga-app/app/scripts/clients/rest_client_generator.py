import os
import re
import sys
from abc import ABC, abstractmethod

import requests


class RestClientGenerator(ABC):

    def __init__(self, server_url, output_dir):
        super().__init__()

        self.server_url = server_url
        self.output_dir = output_dir
        self.version = requests.get(server_url + '/webservices/rest/v2/meta/about').json()['responses'][0]['results'][
            0]['Version'].split('-')[0]
        self.parameters = {}
        self.category = None
        self.subcategory = None
        self.action = None
        self.id1 = None
        self.id2 = None

        self.endpoints = {
            'users/{user}/configs/filters/update': {'method_name': 'update_filters'},
            'users/{user}/configs/filters/{name}/update': {'method_name': 'update_filter'},
            'ga4gh/reads/{study}/{file}': {'method_name': 'fetch_reads'}
        }

    @staticmethod
    def get_category_name(category):
        return category['name']

    @staticmethod
    def get_category_path(category):
        return category['path'].replace('/{apiVersion}/', '')

    @staticmethod
    def get_endpoint_path(endpoint):
        return endpoint['path'].replace('/{apiVersion}/', '')

    @staticmethod
    def get_endpoint_description(endpoint):
        return endpoint['description'] if endpoint['description'].endswith(".") else endpoint['description'] + "."

    @staticmethod
    def get_endpoint_response(endpoint):
        return endpoint['response']

    @staticmethod
    def get_endpoint_method(endpoint):
        return endpoint['method']

    def get_path_params(self, endpoint):
        return re.findall(r'{(.*?)}', self.get_endpoint_path(endpoint))

    def get_mandatory_query_params(self, endpoint):
        path_params = self.get_path_params(endpoint)
        params = []
        for parameter in endpoint['parameters']:
            if parameter['required'] and parameter['name'] not in path_params and parameter['name'] != 'body':
                params.append(parameter['name'])
        return params

    def has_optional_params(self, endpoint):
        for parameter in endpoint['parameters']:
            if parameter['required'] is False:
                return True
        return False

    def is_required(self, parameter):
        return self.parameters[parameter]['required']

    def get_parameter_type(self, parameter):
        return self.parameters[parameter]['type']

    def get_parameter_description(self, parameter):
        return self.parameters[parameter]['description'] if self.parameters[parameter]['description'].endswith(".") \
            else self.parameters[parameter]['description'] + "."

    def get_endpoint_category(self):
        return self.category

    def get_endpoint_subcategory(self):
        return self.subcategory

    def get_endpoint_action(self):
        return self.action

    def get_endpoint_id1(self):
        return self.id1

    def get_endpoint_id2(self):
        return self.id2

    def any_arg(self, items):
        return any([True if '{' in item and '}' in item else False for item in items])

    def all_arg(self, items):
        return all([True if '{' in item and '}' in item else False for item in items])

    def get_method_name(self, endpoint, category):
        if self.get_endpoint_path(endpoint) in self.endpoints:
            return self.endpoints[self.get_endpoint_path(endpoint)]['method_name']

        method_name = ''
        subpath = self.get_endpoint_path(endpoint).replace(self.get_category_path(category) + '/', '')
        items = subpath.split('/')
        if len(items) == 1:
            method_name = items[0]
        if len(items) == 2:
            # e.g. /{apiVersion}/ga4gh/reads/search
            if not self.any_arg(items):
                method_name = '_'.join(items[::-1])
            # e.g. /{apiVersion}/users/{user}/info
            elif self.any_arg([items[0]]) and not self.any_arg([items[1]]):
                method_name = items[1]
        if len(items) == 3:
            # e.g. /{apiVersion}/analysis/variant/cohort/stats/run
            if not self.any_arg(items):
                method_name = '_'.join([items[2], items[0], items[1]])
            # e.g. /{apiVersion}/users/{user}/configs/filters
            elif self.any_arg([items[0]]) and not self.any_arg([items[1:]]):
                method_name = '_'.join([items[2], items[1]])
            # e.g. /{apiVersion}/studies/acl/{members}/update
            elif self.any_arg([items[1]]) and not self.any_arg([items[0], items[2]]):
                method_name = '_'.join([items[2], items[0]])
        if len(items) == 4:
            # e.g. /{apiVersion}/operation/variant/sample/genotype/index
            if not self.any_arg(items):
                method_name = '_'.join([items[3], items[1], items[2]])
        if len(items) == 5:
            # e.g. /{apiVersion}/files/{file}/annotationSets/{annotationSet}/annotations/update
            if self.all_arg([items[0], items[2]]) and not self.any_arg([items[1], items[3], items[4]]):
                method_name = '_'.join([items[4], items[3]])
        if not method_name:
            NotImplementedError('Case not implemented for PATH: "{}"'.format(self.get_endpoint_path(endpoint)))
        return re.sub(r'(?<!^)(?=[A-Z])', '_', method_name).lower()

    @abstractmethod
    def get_imports(self):
        pass

    @abstractmethod
    def get_class_definition(self, category):
        pass

    @abstractmethod
    def get_class_end(self):
        pass

    @abstractmethod
    def get_method_definition(self, category, endpoint):
        pass

    @abstractmethod
    def get_file_name(self, category):
        pass

    def create_rest_clients(self):
        for category in requests.get(self.server_url + '/webservices/rest/v2/meta/api').json()['responses'][0]['results'][0]:
            text = []
            text.append(self.get_class_definition(category))

            for endpoint in category['endpoints']:
                # We update the dictionary of parameters of the endpoint
                self.parameters = {}
                for parameter in endpoint['parameters']:
                    self.parameters[parameter['name'] if parameter['name'] != 'body' else 'data'] = parameter

                # We extract the resources of the endpoint
                self.parse_resources(category, endpoint)

                text.append(self.get_method_definition(category, endpoint))

            text.append(self.get_class_end())
            # Now, we put in the first position the imports.
            text.insert(0, self.get_imports())

            # Choose the file name to be created
            file_name = self.get_file_name(category)
            sys.stderr.write('Creating ' + os.path.join(self.output_dir, file_name) + '...\n')
            with open(os.path.join(self.output_dir, file_name), 'w') as fhand:
                fhand.write('\n'.join(text))

    def parse_resources(self, category, endpoint):
        self.category = None
        self.subcategory = None
        self.action = None
        self.id1 = None
        self.id2 = None

        path_params = self.get_path_params(endpoint)
        endpoint_subpath = endpoint['path'].replace(category['path'] + '/', '')

        if path_params:
            regex = ''.join(['{' + i + '}(.+)' for i in path_params])
            resources = re.findall(regex, endpoint_subpath)
            if resources:
                resources = resources if type(resources[0]) != tuple else list(resources[0])
        else:
            resources = [endpoint_subpath]
        resources = [i.strip('/') for i in resources]

        if len(resources) == 1:
            self.action = resources[0]
        elif len(resources) > 1:
            if '/' in resources[1]:
                splitted = resources[1].split('/')
                self.category = resources[0]
                self.subcategory = '/'.join(splitted[:(len(splitted) - 1)])
                self.action = splitted[-1]
            else:
                self.subcategory = resources[0]
                self.action = resources[1]
        if path_params:
            self.id1 = path_params[0]
        if len(path_params) > 1:
            self.id2 = path_params[1]
