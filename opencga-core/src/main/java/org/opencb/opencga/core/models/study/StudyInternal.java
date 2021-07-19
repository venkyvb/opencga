/*
 * Copyright 2015-2020 OpenCB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opencb.opencga.core.models.study;

import org.opencb.opencga.core.models.common.Status;
import org.opencb.opencga.core.models.study.configuration.StudyConfiguration;

public class StudyInternal {

    private Status status;
    private StudyIndex index;
    private StudyConfiguration configuration;

    public StudyInternal() {
    }

    public StudyInternal(Status status, StudyIndex index, StudyConfiguration configuration) {
        this.status = status;
        this.index = index;
        this.configuration = configuration;
    }

    public static StudyInternal init() {
        return new StudyInternal(new Status(), StudyIndex.init(), StudyConfiguration.init());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StudyInternal{");
        sb.append("status=").append(status);
        sb.append(", index=").append(index);
        sb.append(", configuration=").append(configuration);
        sb.append('}');
        return sb.toString();
    }

    public Status getStatus() {
        return status;
    }

    public StudyInternal setStatus(Status status) {
        this.status = status;
        return this;
    }

    public StudyIndex getIndex() {
        return index;
    }

    public StudyInternal setIndex(StudyIndex index) {
        this.index = index;
        return this;
    }

    public StudyConfiguration getConfiguration() {
        return configuration;
    }

    public StudyInternal setConfiguration(StudyConfiguration configuration) {
        this.configuration = configuration;
        return this;
    }
}
