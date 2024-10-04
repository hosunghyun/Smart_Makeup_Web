CREATE TABLE member (
    member_id VARCHAR(50) NOT NULL UNIQUE,
	member_password VARCHAR(100) NOT NULL,
    email VARCHAR(50) NOT NULL,
    phone VARCHAR(20),
    PRIMARY KEY(member_id)
);

CREATE TABLE makeup (
	makeup_id BIGINT AUTO_INCREMENT PRIMARY KEY,
	member_id VARCHAR(50),
	color_code VARCHAR(20) NOT NULL,
	opacity INT NOT NULL,
	FOREIGN KEY (member_id) REFERENCES member (member_id) ON DELETE CASCADE
);

CREATE TABLE board (
	board_id BIGINT AUTO_INCREMENT PRIMARY KEY,
	member_id VARCHAR(50) NOT NULL,
    title VARCHAR(50) NOT NULL,
    content_text TEXT,
	FOREIGN KEY (member_id) REFERENCES member (member_id) ON DELETE CASCADE
);

CREATE TABLE product (
	product_code BIGINT AUTO_INCREMENT PRIMARY KEY,
	product_name VARCHAR(50) NOT NULL,
	Price INT NOT NULL
);

CREATE TABLE image (
	image_code BIGINT AUTO_INCREMENT PRIMARY KEY,
    board_id BIGINT,
    product_code BIGINT,
	image_link VARCHAR(100) NOT NULL,
    FOREIGN KEY (board_id) REFERENCES board (board_id) ON DELETE CASCADE,
    FOREIGN KEY (product_code) REFERENCES product (product_code) ON DELETE CASCADE
);

CREATE TABLE product_type (
	product_type_id INT AUTO_INCREMENT PRIMARY KEY,
	makeup_id BIGINT NOT NULL,
	product_code BIGINT NOT NULL,
	product_type_name VARCHAR(50) NOT NULL,
	FOREIGN KEY (makeup_id) REFERENCES makeup (makeup_id) ON DELETE CASCADE,
	FOREIGN KEY (product_code) REFERENCES product (product_code) ON DELETE CASCADE
);

CREATE TABLE comment (
	comment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
	board_id BIGINT NOT NULL,
    member_id VARCHAR(50) NOT NULL,
    comment_content TEXT NOT NULL,
	FOREIGN KEY (board_id) REFERENCES board (board_id) ON DELETE CASCADE,
	FOREIGN KEY (member_id) REFERENCES member (member_id) ON DELETE CASCADE
);