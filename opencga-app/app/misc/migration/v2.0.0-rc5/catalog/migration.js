print("\nAdding new interpretation indexes...")
db.interpretation.createIndex({"clinicalAnalysisId": 1, "studyUid": 1}, {"background": true});
db.interpretation.createIndex({"status": 1, "studyUid": 1}, {"background": true});
db.interpretation.createIndex({"primaryFindings.id": 1, "studyUid": 1}, {"background": true});
db.interpretation.createIndex({"secondaryFindings.id": 1, "studyUid": 1}, {"background": true});

db.interpretation.createIndex({"uuid": 1, "version": 1}, {"unique": true, "background": true});
db.interpretation.createIndex({"uid": 1, "version": 1}, {"unique": true, "background": true});
db.interpretation.createIndex({"id": 1, "version": 1, "studyUid": 1}, {"unique": true, "background": true});
db.interpretation.dropIndex({"uuid": 1})
db.interpretation.dropIndex({"uid": 1})
db.interpretation.dropIndex({"id": 1, "studyUid": 1})