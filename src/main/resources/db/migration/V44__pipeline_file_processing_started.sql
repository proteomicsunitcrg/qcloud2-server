ALTER TABLE `pipeline_file`
  ADD COLUMN `processing_started_date` datetime DEFAULT NULL AFTER `received_date`;
