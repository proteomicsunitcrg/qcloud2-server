ALTER TABLE `chart` 
ADD COLUMN `param_id` BIGINT(20) NULL AFTER `sample_type_id`,
ADD INDEX `FKparamId234234_idx` (`param_id` ASC);
ALTER TABLE `chart` 
ADD CONSTRAINT `FKparamId234234`
  FOREIGN KEY (`param_id`)
  REFERENCES `param` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

update chart c
inner join chart_params cp on c.id = cp.chart_id
set c.param_id = cp.param_id;

ALTER TABLE `chart_params` 
DROP FOREIGN KEY `FKt6ild58v7yjkxa1ow6ga0mtvi`;
ALTER TABLE `chart_params` 
DROP COLUMN `param_id`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`chart_id`, `context_source_id`),
DROP INDEX `FKt6ild58v7yjkxa1ow6ga0mtvi`;

