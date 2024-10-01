CREATE TABLE member (
    member_id VARCHAR(20) NOT NULL UNIQUE,
	member_password VARCHAR(100) NOT NULL,
    email VARCHAR(30) NOT NULL,
    phone VARCHAR(13),
    PRIMARY KEY(member_id)
);

CREATE TABLE makeup (
	makeup_id INT AUTO_INCREMENT PRIMARY KEY,
	member_id VARCHAR(20),
	color_code VARCHAR(20) NOT NULL,
	opacity INT NOT NULL,
	FOREIGN KEY (member_id) REFERENCES member (member_id) ON DELETE CASCADE
);

CREATE TABLE board (
	board_id INT AUTO_INCREMENT PRIMARY KEY,
	member_id VARCHAR(20) NOT NULL,
    title VARCHAR(20) NOT NULL,
    content_text TEXT,
	FOREIGN KEY (member_id) REFERENCES member (member_id) ON DELETE CASCADE
);

CREATE TABLE product (
	product_code INT AUTO_INCREMENT PRIMARY KEY,
	product_name VARCHAR(20) NOT NULL,
	Price INT NOT NULL
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
	product_type_id INT AUTO_INCREMENT PRIMARY KEY,
	makeup_id INT NOT NULL,
	product_code INT NOT NULL,
	product_type_name VARCHAR(20) NOT NULL,
	FOREIGN KEY (makeup_id) REFERENCES makeup (makeup_id) ON DELETE CASCADE,
	FOREIGN KEY (product_code) REFERENCES product (product_code) ON DELETE CASCADE
);

CREATE TABLE comment (
	comment_id INT AUTO_INCREMENT PRIMARY KEY,
	board_id INT NOT NULL,
    member_id VARCHAR(20) NOT NULL,
    comment_content TEXT NOT NULL,
	FOREIGN KEY (board_id) REFERENCES board (board_id) ON DELETE CASCADE,
	FOREIGN KEY (member_id) REFERENCES member (member_id) ON DELETE CASCADE
);