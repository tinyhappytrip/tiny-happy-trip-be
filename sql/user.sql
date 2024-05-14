CREATE SCHEMA IF NOT EXISTS tinyhappytrip;

DROP TABLE IF EXISTS `tokens`;
DROP TABLE IF EXISTS `follows`;
DROP TABLE IF EXISTS `users`;

CREATE TABLE IF NOT EXISTS `users` (
    `user_id` INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `email` VARCHAR(40) UNIQUE NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `birthdate` DATE NOT NULL,
    `nickname` VARCHAR(10) NULL,
    `role` ENUM('ADMIN', 'USER') NOT NULL DEFAULT 'USER',
    `introduction` VARCHAR(30) NULL,
    `social_type` ENUM('NAVER', 'KAKAO', 'GOOGLE', 'EMAIL') NOT NULL,
    `user_image` VARCHAR(255) NULL
);

CREATE TABLE IF NOT EXISTS `follows` (
    `follow_id` INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `followee_id` INT NOT NULL,
    `follower_id` INT NOT NULL,
    FOREIGN KEY (`followee_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
    FOREIGN KEY (`follower_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
    UNIQUE (`followee_id`, `follower_id`)
);

CREATE TABLE IF NOT EXISTS `tokens` (
    `token_id` INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `user_id` INT NOT NULL,
    `refresh_token` VARCHAR(255) NOT NULL,
    `expires_at` TIMESTAMP NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
);

SELECT * FROM users;
SELECT * FROM follows;

 /*이 라인 삭제하고 테스트--; 
INSERT INTO `users` (`email`, `password`, `birthDate`, `nickname`, `role`, `introduction`, `social_type`) VALUES ('user', '1234', NOW(), 'user', 'USER', '안녕', 'NAVER');
INSERT INTO `users` (`email`, `password`, `birthDate`, `nickname`, `role`, `introduction`, `social_type`) VALUES ('admin', '1234', CURRENT_TIMESTAMP(), 'admin', 'ADMIN', '안녕', 'KAKAO');