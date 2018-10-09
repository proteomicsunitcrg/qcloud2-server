ALTER TABLE `qcloud2`.`threshold` 
ADD COLUMN `non_conformity_direction` VARCHAR(255) NULL AFTER `sample_type_id`;

