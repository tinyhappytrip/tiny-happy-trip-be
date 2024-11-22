DROP TABLE IF EXISTS `chats`;
DROP TABLE IF EXISTS `chat_rooms`;
DROP TABLE IF EXISTS `notifications`;

CREATE TABLE IF NOT EXISTS `chat_rooms` (
	`chat_room_id` INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	`participant_id1` INT NOT NULL,
	`participant_id2` INT NOT NULL,
	FOREIGN KEY (`participant_id1`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
	FOREIGN KEY (`participant_id2`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `chats` (
    `chat_id` INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `chat_room_id` INT NOT NULL,
    `sender_id` INT NOT NULL,
    `receiver_id` INT NOT NULL,
    `message` TEXT NOT NULL,
    `sent_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `is_read` BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (`chat_room_id`) REFERENCES `chat_rooms` (`chat_room_id`) ON DELETE CASCADE,
    FOREIGN KEY (`sender_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
    FOREIGN KEY (`receiver_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE 
);

CREATE TABLE `notifications` (
    `notification_id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT NOT NULL,
    `content` TEXT,
    `is_read` BOOLEAN DEFAULT FALSE,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
);

SELECT * FROM chats;
SELECT * FROM chat_rooms;
SELECT * FROM notifications;
        SELECT chat_room_id
        FROM chat_rooms
        WHERE (participant_id1 = 4 AND participant_id2 = 2)
           OR (participant_id1 = 2 AND participant_id2 = 4);