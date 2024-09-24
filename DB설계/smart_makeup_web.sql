CREATE TABLE `User` (
  `id` int PRIMARY KEY,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone` varchar(255)
);

CREATE TABLE `Makeup` (
  `id` int PRIMARY KEY,
  `user_id` int,
  `makeup_type` varchar(255) NOT NULL,
  `color_code` varchar(255) NOT NULL,
  `transparency` varchar(255) NOT NULL
);

CREATE TABLE `Product` (
  `code` int PRIMARY KEY,
  `makeup_id` int,
  `color_code` varchar(255),
  `image_code` int,
  `name` varchar(255) NOT NULL,
  `price` decimal NOT NULL
);

CREATE TABLE `Board` (
  `id` int PRIMARY KEY,
  `user_id` int,
  `image_code` int,
  `title` varchar(255) NOT NULL,
  `content` text
);

CREATE TABLE `Comment` (
  `id` int PRIMARY KEY,
  `board_id` int,
  `user_id` int,
  `content` text NOT NULL
);

CREATE TABLE `Image` (
  `id` int PRIMARY KEY,
  `image_link` varchar(255) NOT NULL
);

ALTER TABLE `Makeup` ADD FOREIGN KEY (`user_id`) REFERENCES `User` (`id`);

ALTER TABLE `Product` ADD FOREIGN KEY (`makeup_id`) REFERENCES `Makeup` (`id`);

ALTER TABLE `Product` ADD FOREIGN KEY (`color_code`) REFERENCES `Makeup` (`color_code`);

ALTER TABLE `Product` ADD FOREIGN KEY (`image_code`) REFERENCES `Image` (`id`);

ALTER TABLE `Board` ADD FOREIGN KEY (`user_id`) REFERENCES `User` (`id`);

ALTER TABLE `Board` ADD FOREIGN KEY (`image_code`) REFERENCES `Image` (`id`);

ALTER TABLE `Comment` ADD FOREIGN KEY (`board_id`) REFERENCES `Board` (`id`);

ALTER TABLE `Comment` ADD FOREIGN KEY (`user_id`) REFERENCES `User` (`id`);
