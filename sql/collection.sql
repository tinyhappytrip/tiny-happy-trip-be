DROP TABLE IF EXISTS `collection_likes`;
DROP TABLE IF EXISTS `collection_items`;
DROP TABLE IF EXISTS `collection_comments`;
DROP TABLE IF EXISTS `collections`;

CREATE TABLE `collections` (
    `collection_id` INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `user_id` INT NOT NULL,
    `title` VARCHAR(50) NOT NULL,
    `description` VARCHAR(200) NULL,
    `scope` ENUM('PUBLIC', 'PRIVATE', 'FOLLOWER') NOT NULL,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `music_keyword` VARCHAR(50) NULL,
    FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
);

CREATE TABLE `collection_items` (
    `collection_item_id` INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `collection_id` INT NOT NULL,
    `story_id` INT NULL,
    FOREIGN KEY (`collection_id`) REFERENCES `collections` (`collection_id`) ON DELETE CASCADE,
    FOREIGN KEY (`story_id`) REFERENCES `stories` (`story_id`) ON DELETE CASCADE
);

CREATE TABLE `collection_likes` (
    `collection_like_id` INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `collection_id` INT NOT NULL,
    `user_id` INT NOT NULL,
    FOREIGN KEY (`collection_id`) REFERENCES `collections` (`collection_id`) ON DELETE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
);

CREATE TABLE `collection_hashtags` (
    `collection_hashtag_id` INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `collection_id` INT NOT NULL,
    `hashtag` VARCHAR(10) NOT NULL,
    FOREIGN KEY (`collection_id`) REFERENCES `collections` (`collection_id`) ON DELETE CASCADE
);

CREATE TABLE `collection_comments` (
    `collection_comment_id` INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `user_id` INT NOT NULL,
    `collection_id` INT NOT NULL,
    `content` VARCHAR(100) NOT NULL,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
     FOREIGN KEY (`collection_id`) REFERENCES `collections` (`collection_id`) ON DELETE CASCADE
);

SELECT * FROM collections;