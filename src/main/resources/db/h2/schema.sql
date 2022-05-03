CREATE TABLE IF NOT EXISTS `user` (
                        `id`	bigint NOT NULL	AUTO_INCREMENT PRIMARY KEY ,
                        `username`	varchar(255) NOT NULL UNIQUE ,
                        `password`	varchar(255) NOT NULL ,
                        `nickname`	varchar(255) NOT NULL UNIQUE ,
                        `reg_date`	TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
                        `active`	boolean	NOT NULL DEFAULT true
);

CREATE TABLE IF NOT EXISTS `role` (
                        `id`	bigint NOT NULL	AUTO_INCREMENT PRIMARY KEY ,
                        `name`	varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS `user_roles` (
                              `user_id`	bigint	NOT NULL ,
                              `role_id`	bigint	NOT NULL ,
                              PRIMARY KEY(user_id, role_id) ,
                              FOREIGN KEY (user_id)
                              REFERENCES user(id) ,
                              FOREIGN KEY (role_id)
                              REFERENCES role(id)
);

CREATE TABLE IF NOT EXISTS `user-profile` (
                                `id`	bigint	NOT NULL	PRIMARY KEY ,
                                `original_name`	varchar(255)	NULL,
                                `name`	varchar(255)	NOT NULL	DEFAULT 'default_profile.png',
                                `path`	varchar(255)	NULL,
                                `size`	varchar(255)	NULL,
                                FOREIGN KEY (id)
                                REFERENCES user(id)
);

CREATE TABLE IF NOT EXISTS `loginLog` (
                              `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY ,
                              `user_id`	bigint	NOT NULL ,
                              `ip` varchar(255) NOT NULL ,
                              `date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              FOREIGN KEY (user_id)
                              REFERENCES user(id)
);

CREATE TABLE IF NOT EXISTS `category` (
                            `id`	bigint	NOT NULL AUTO_INCREMENT PRIMARY KEY ,
                            `name`	varchar(255)	NOT NULL
);

CREATE TABLE IF NOT EXISTS `tour` (
                        `id`	bigint	NOT NULL	AUTO_INCREMENT PRIMARY KEY ,
                        `name`	varchar(255)	NOT NULL,
                        `description`	varchar(255)	NOT NULL,
                        `date`	TIMESTAMP	NOT NULL	DEFAULT CURRENT_TIMESTAMP,
                        `active`	boolean	NOT NULL	DEFAULT false,
                        `category_id`	bigint	NOT NULL,
                        FOREIGN KEY (category_id)
                        REFERENCES category(id)
);

CREATE TABLE IF NOT EXISTS `place` (
                         `id`	bigint	NOT NULL	AUTO_INCREMENT PRIMARY KEY ,
                         `name`	varchar(255)	NOT NULL,
                         `latitude`	varchar(255)	NOT NULL,
                         `longitude`	varchar(255)	NOT NULL,
                         `address`	varchar(255)	NOT NULL,
                         `description`	varchar(255)	NOT NULL,
                         `guide`	varchar(255)	NOT NULL,
                         `date`	TIMESTAMP	NOT NULL	DEFAULT CURRENT_TIMESTAMP,
                         `active`	boolean	NOT NULL	DEFAULT true
);

CREATE TABLE IF NOT EXISTS `tour-places` (
                               `tour_id`	bigint	NOT NULL,
                               `place_id`	bigint	NOT NULL,
                               `order`	tinyint	NOT NULL,
                               PRIMARY KEY (tour_id, place_id),
                               FOREIGN KEY (tour_id)
                               REFERENCES tour(id),
                               FOREIGN KEY (place_id)
                               REFERENCES place(id)
);

CREATE TABLE IF NOT EXISTS `review` (
                          `id`	bigint	NOT NULL	AUTO_INCREMENT PRIMARY KEY,
                          `user_id`	bigint	NOT NULL,
                          `tour_id`	bigint	NOT NULL,
                          `title`	varchar(255)	NOT NULL,
                          `content`	varchar(255)	NOT NULL,
                          `date`	TIMESTAMP	NOT NULL	DEFAULT CURRENT_TIMESTAMP,
                          `count`	int	NOT NULL,
                          `good`	int	NOT NULL,
                          `bad`	int	NOT NULL,
                          `point`	varchar(255)	NOT NULL,
                          `active`	boolean	NOT NULL	DEFAULT TRUE,
                          FOREIGN KEY (user_id)
                          REFERENCES user(id),
                          FOREIGN KEY (tour_id)
                          REFERENCES tour(id)
);

CREATE TABLE IF NOT EXISTS `review-images` (
                                 `id`	bigint	NOT NULL	AUTO_INCREMENT PRIMARY KEY ,
                                 `review_id`	bigint	NOT NULL,
                                 `original_name`	varchar(255)	NOT NULL,
                                 `name`	varchar(255)	NOT NULL,
                                 `path`	varchar(255)	NOT NULL,
                                 `size`	varchar(255)	NOT NULL,
                                 `active`	boolean	NOT NULL	DEFAULT true,
                                 FOREIGN KEY (review_id)
                                 REFERENCES review(id)
);

CREATE TABLE IF NOT EXISTS `review_good` (
                                `review_id`	bigint	NOT NULL,
                                `user_id`	bigint	NOT NULL,
                                PRIMARY KEY (review_id, user_id),
                                FOREIGN KEY (review_id)
                                REFERENCES review(id),
                                FOREIGN KEY (user_id)
                                REFERENCES user(id)
);

CREATE TABLE IF NOT EXISTS `review_bad` (
                                `review_id`	bigint	NOT NULL,
                                `user_id`	bigint	NOT NULL,
                                PRIMARY KEY (review_id, user_id),
                                FOREIGN KEY (review_id)
                                REFERENCES review(id),
                                FOREIGN KEY (user_id)
                                REFERENCES user(id)
);

CREATE TABLE IF NOT EXISTS `comment` (
                           `id`	bigint	NOT NULL	AUTO_INCREMENT PRIMARY KEY ,
                           `review_id`	bigint	NOT NULL,
                           `user_id`	bigint	NOT NULL,
                           `content`	varchar(255)	NOT NULL,
                           `date`	TIMESTAMP	NOT NULL	DEFAULT CURRENT_TIMESTAMP,
                           `active`	boolean	NOT NULL	DEFAULT true,
                           FOREIGN KEY (review_id)
                           REFERENCES review(id),
                           FOREIGN KEY (user_id)
                           REFERENCES user(id)
);

CREATE TABLE IF NOT EXISTS `user-tours` (
                              `user_id`	bigint	NOT NULL,
                              `tour_id`	bigint	NOT NULL,
                              FOREIGN KEY (user_id)
                              REFERENCES user(id),
                              FOREIGN KEY (tour_id)
                              REFERENCES tour(id)
);

CREATE TABLE IF NOT EXISTS `notice` (
                          `id`	bigint	NOT NULL	AUTO_INCREMENT PRIMARY KEY ,
                          `title`	varchar(255)	NOT NULL,
                          `content`	varchar(255)	NOT NULL,
                          `date`	TIMESTAMP	NOT NULL,
                          `active`	boolean	NOT NULL	DEFAULT true
);

CREATE TABLE IF NOT EXISTS `ask` (
                       `id`	bigint	NOT NULL	AUTO_INCREMENT PRIMARY KEY ,
                       `user_id`	bigint	NOT NULL,
                       `title`	varchar(255)	NOT NULL,
                       `content`	varchar(255)	NOT NULL,
                       `date`	TIMESTAMP	NOT NULL	DEFAULT CURRENT_TIMESTAMP,
                       `answer`	varchar(255)	NULL,
                       `active`	boolean	NOT NULL	DEFAULT true,
                       FOREIGN KEY (user_id)
                       REFERENCES user(id)
);