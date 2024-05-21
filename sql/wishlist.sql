DROP TABLE IF EXISTS `goto_items`;
DROP TABLE IF EXISTS `goto_lists`;

CREATE TABLE `goto_lists` (
    `goto_list_id` INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `title` VARCHAR(255) NULL,
    `user_id` INT NOT NULL,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
);

CREATE TABLE `goto_items` (
    `goto_item_id` INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `goto_list_id` INT NOT NULL,
    `place_name` VARCHAR(255) NULL,
    `description` VARCHAR(255) NULL,
    `place_id` INT NULL,
    `place_image` VARCHAR(255) NULL,
    `latitude` DOUBLE NULL,
    `longitude` DOUBLE NULL,
    FOREIGN KEY (`goto_list_id`) REFERENCES `goto_lists` (`goto_list_id`) ON DELETE CASCADE
);

select * from goto_lists;
select * from goto_items;