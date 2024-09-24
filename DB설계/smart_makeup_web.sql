CREATE TABLE `User` (
  `id` int PRIMARY KEY,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone` varchar(255)
);

CREATE TABLE `Makeup` (
  `makeup_id` int PRIMARY KEY,
  `user_id` int,
  `color_code` varchar(255) NOT NULL,
  `opacity` int NOT NULL
);

CREATE TABLE `ProductType` (
  `makeup_id` int,
  `product_code` int,
  `product_type_name` varchar(255) NOT NULL
);

CREATE TABLE `Product` (
  `product_code` int PRIMARY KEY,
  `image_code` int,
  `product_name` varchar(255) NOT NULL,
  `price` decimal NOT NULL
);

CREATE TABLE `Board` (
  `board_id` int PRIMARY KEY,
  `user_id` int,
  `image_code` int,
  `title` varchar(255) NOT NULL,
  `content` text
);

CREATE TABLE `Comment` (
  `comment_id` int PRIMARY KEY,
  `board_id` int,
  `user_id` int,
  `comment_content` text NOT NULL
);

CREATE TABLE `ImageLink` (
  `image_code` int PRIMARY KEY,
  `image_link` varchar(255) NOT NULL
);

ALTER TABLE `Makeup` ADD FOREIGN KEY (`user_id`) REFERENCES `User` (`id`);

ALTER TABLE `ProductType` ADD FOREIGN KEY (`makeup_id`) REFERENCES `Makeup` (`makeup_id`);

ALTER TABLE `ProductType` ADD FOREIGN KEY (`product_code`) REFERENCES `Product` (`product_code`);

ALTER TABLE `Product` ADD FOREIGN KEY (`image_code`) REFERENCES `ImageLink` (`image_code`);

ALTER TABLE `Board` ADD FOREIGN KEY (`user_id`) REFERENCES `User` (`id`);

ALTER TABLE `Board` ADD FOREIGN KEY (`image_code`) REFERENCES `ImageLink` (`image_code`);

ALTER TABLE `Comment` ADD FOREIGN KEY (`board_id`) REFERENCES `Board` (`board_id`);

ALTER TABLE `Comment` ADD FOREIGN KEY (`user_id`) REFERENCES `User` (`id`);
