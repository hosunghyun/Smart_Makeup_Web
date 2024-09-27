CREATE TABLE user (
    user_id VARCHAR(20) NOT NULL UNIQUE,
    user_password VARCHAR(20) NOT NULL,
    email VARCHAR(30) NOT NULL,
    phone VARCHAR(13),
    PRIMARY KEY(user_id)
);

CREATE TABLE makeup (
	makeup_id INT AUTO_INCREMENT PRIMARY KEY,
	user_id VARCHAR(20),
	color_code VARCHAR(20) NOT NULL,
	opacity INT NOT NULL,
	FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE CASCADE
);

CREATE TABLE board (
	board_id INT AUTO_INCREMENT PRIMARY KEY,
	user_id VARCHAR(20),
	-- image_code INT,
    title VARCHAR(20) NOT NULL,
    content_text TEXT,
	FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE CASCADE
	-- FOREIGN KEY (image_code) REFERENCES image_link (image_code) ON DELETE SET NULL
);

CREATE TABLE product (
	product_code INT AUTO_INCREMENT PRIMARY KEY,
	-- image_code INT,
	product_name VARCHAR(20) NOT NULL,
	Price INT NOT NULL
	-- FOREIGN KEY (image_code) REFERENCES image_link (image_code) ON DELETE SET NULL
);

CREATE TABLE image (
	image_code INT AUTO_INCREMENT PRIMARY KEY,
    board_id INT,
    product_code INT,
	image_link VARCHAR(20) NOT NULL,
    FOREIGN KEY (board_id) REFERENCES board (board_id) ON DELETE CASCADE,
    FOREIGN KEY (product_code) REFERENCES product (product_code) ON DELETE CASCADE
);

CREATE TABLE product_type (
	makeup_id INT,
	product_code INT,
	product_type_name VARCHAR(20) NOT NULL,
	FOREIGN KEY (makeup_id) REFERENCES makeup (makeup_id) ON DELETE CASCADE,
	FOREIGN KEY (product_code) REFERENCES product (product_code) ON DELETE CASCADE
);

CREATE TABLE comment (
	comment_id INT AUTO_INCREMENT PRIMARY KEY,
	board_id INT,
    user_id VARCHAR(20),
    comment_content TEXT NOT NULL,
	FOREIGN KEY (board_id) REFERENCES board (board_id) ON DELETE CASCADE,
	FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE CASCADE
);