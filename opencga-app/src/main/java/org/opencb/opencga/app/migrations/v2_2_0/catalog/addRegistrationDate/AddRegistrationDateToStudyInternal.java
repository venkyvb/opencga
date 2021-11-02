package org.opencb.opencga.app.migrations.v2_2_0.catalog.addRegistrationDate;

import org.opencb.opencga.catalog.db.mongodb.MongoDBAdaptorFactory;
import org.opencb.opencga.catalog.migration.Migration;

@Migration(id = "add_registrationDate_to_study.internal", description = "Add registrationDate to Study #1804", version = "2.2.0",
        language = Migration.MigrationLanguage.JAVA, domain = Migration.MigrationDomain.CATALOG,
        date = 20210720)
public class AddRegistrationDateToStudyInternal extends AddRegistrationDate {

    @Override
    protected void run() throws Exception {
        addRegistrationDate(MongoDBAdaptorFactory.STUDY_COLLECTION);
    }
}
