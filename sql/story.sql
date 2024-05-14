DROP TABLE IF EXISTS `story_tags`;
DROP TABLE IF EXISTS `story_likes`;
DROP TABLE IF EXISTS `story_comments`;
DROP TABLE IF EXISTS `story_hashtags`;
DROP TABLE IF EXISTS `story_images`;
DROP TABLE IF EXISTS `stories`;

CREATE TABLE IF NOT EXISTS `stories` (
    `story_id` INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `user_id` INT NOT NULL,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `content` VARCHAR(200) NOT NULL,
    `weather` VARCHAR(20) NOT NULL,
    `emotion` VARCHAR(20) NOT NULL,
    `location` VARCHAR(255) NOT NULL,
    `scope` ENUM('PUBLIC', 'PRIVATE', 'FOLLOWER') NOT NULL,
    `latitude` DOUBLE NOT NULL,
    `longitude` DOUBLE NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `story_images` (
    `story_image_id` INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `story_id` INT NOT NULL,
    `story_image_name` VARCHAR(255) NOT NULL,
    `story_image_path` VARCHAR(255) NOT NULL,
    FOREIGN KEY (`story_id`) REFERENCES `stories` (`story_id`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `story_hashtags` (
    `story_hashtag_id` INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `story_id` INT NOT NULL,
    `hashtag` VARCHAR(15) NOT NULL,
    FOREIGN KEY (`story_id`) REFERENCES `stories` (`story_id`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `story_comments` (
    `story_comment_id` INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `story_reply_id` INT NULL,
    `user_id` INT NOT NULL,
    `story_id` INT NOT NULL,
    `content` VARCHAR(100) NOT NULL,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`story_reply_id`) REFERENCES `story_comments` (`story_comment_id`) ON DELETE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
    FOREIGN KEY (`story_id`) REFERENCES `stories` (`story_id`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `story_likes` (
    `story_like_id` INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `story_id` INT NOT NULL,
    `user_id` INT NOT NULL,
    FOREIGN KEY (`story_id`) REFERENCES `stories` (`story_id`) ON DELETE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `story_tags` (
    `story_tags_id` INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `user_id` INT NOT NULL,
    `story_id` INT NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
    FOREIGN KEY (`story_id`) REFERENCES `stories` (`story_id`) ON DELETE CASCADE
);

SELECT * FROM `story_tags`;
SELECT * FROM `story_likes`;
SELECT * FROM `story_comments`;
SELECT * FROM `story_hashtags`;
SELECT * FROM `story_images`;
SELECT * FROM `stories`;