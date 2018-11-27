ALTER TABLE `context_source` 
ADD COLUMN `trace_color_id` BIGINT(20) NULL AFTER `sequence`,
ADD COLUMN `shade_grade` INT(11) NOT NULL DEFAULT 0 AFTER `trace_color_id`,
ADD INDEX `fk_trace_color_index` (`trace_color_id` ASC);
ALTER TABLE `context_source` 
ADD CONSTRAINT `fk_context_source_1`
  FOREIGN KEY (`trace_color_id`)
  REFERENCES `trace_color` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
