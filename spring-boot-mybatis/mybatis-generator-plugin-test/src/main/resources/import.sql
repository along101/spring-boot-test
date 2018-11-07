DROP TABLE IF EXISTS city;

CREATE TABLE city (
id INT not null auto_increment COMMENT '主键',
name VARCHAR(20) COMMENT '名称',
state VARCHAR(20) COMMENT '州',
country VARCHAR(20) COMMENT '国家',
PRIMARY KEY (`id`));

INSERT INTO city(name, state, country)
VALUES ('San Francisco', 'CA', 'US');
