DROP TABLE IF EXISTS `playlist_hashtags`;
DROP TABLE IF EXISTS `playlist_likes`;
DROP TABLE IF EXISTS `playlist_items`;
DROP TABLE IF EXISTS `playlists`;

CREATE TABLE `playlists` (
    `playlist_id` INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `user_id` INT NOT NULL,
    `title` VARCHAR(50) NOT NULL,
    `description` VARCHAR(200) NULL,
    `scope` ENUM('PUBLIC', 'PRIVATE', 'FOLLOWER') NOT NULL,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
);

CREATE TABLE `playlist_items` (
    `playlist_item_id` INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `playlist_id` INT NOT NULL,
    `story_id` INT NULL,
    `title` VARCHAR(50) NOT NULL,
    FOREIGN KEY (`playlist_id`) REFERENCES `playlists` (`playlist_id`) ON DELETE CASCADE,
    FOREIGN KEY (`story_id`) REFERENCES `stories` (`story_id`) ON DELETE CASCADE
);

CREATE TABLE `playlist_likes` (
    `playlist_like_id` INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `playlist_id` INT NOT NULL,
    `user_id` INT NOT NULL,
    FOREIGN KEY (`playlist_id`) REFERENCES `playlists` (`playlist_id`) ON DELETE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
);

CREATE TABLE `playlist_hashtags` (
    `playlist_hashtag_id` INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `playlist_id` INT NOT NULL,
    `hashtag` VARCHAR(10) NOT NULL,
    FOREIGN KEY (`playlist_id`) REFERENCES `playlists` (`playlist_id`) ON DELETE CASCADE
);
