CREATE DATABASE smart_makeup;
USE smart_makeup;

CREATE TABLE member (	-- 사용자 테이블 
    member_id VARCHAR(50) NOT NULL UNIQUE,
	member_password VARCHAR(100) NOT NULL,
    email VARCHAR(50) NOT NULL,
    phone VARCHAR(20),
    PRIMARY KEY(member_id)
);

CREATE TABLE product_category (
	category VARCHAR(50) NOT NULL UNIQUE,
    PRIMARY KEY(category)
);

CREATE TABLE makeup (	-- 화장 정보를 저장하기 위한 테이블 
	makeup_id BIGINT AUTO_INCREMENT PRIMARY KEY,
	member_id VARCHAR(50),
    category VARCHAR(50),
    button_number INT,
	color_code VARCHAR(20) NOT NULL,
	opacity INT NOT NULL,
	FOREIGN KEY (member_id) REFERENCES member (member_id) ON DELETE CASCADE,
    FOREIGN KEY (category) REFERENCES product_category (category) ON DELETE CASCADE
);

CREATE TABLE board (	-- 게시판을 저장하기 위한 테이블 
	board_id BIGINT AUTO_INCREMENT PRIMARY KEY,
	member_id VARCHAR(50) NOT NULL,
    title VARCHAR(50) NOT NULL,
    content_text TEXT,
	FOREIGN KEY (member_id) REFERENCES member (member_id) ON DELETE CASCADE
);

CREATE TABLE product (	-- 광고를 위한 화장품 정보 테이블 
	product_code BIGINT AUTO_INCREMENT PRIMARY KEY,
	product_name VARCHAR(50) NOT NULL,
    category VARCHAR(50) NOT NULL,
    product_link VARCHAR(50) NOT NULL,
	Price INT NOT NULL,
    FOREIGN KEY (category) REFERENCES product_category (category) ON DELETE CASCADE
);

CREATE TABLE image (	-- 이미지 링크를 위한 테이블 
	image_code BIGINT AUTO_INCREMENT PRIMARY KEY,
    board_id BIGINT,
    product_code BIGINT,
	image_link VARCHAR(100) NOT NULL,
    FOREIGN KEY (board_id) REFERENCES board (board_id) ON DELETE CASCADE,
    FOREIGN KEY (product_code) REFERENCES product (product_code) ON DELETE CASCADE
);

CREATE TABLE comment (	-- 댓글 저장을 위한 테이블 
	comment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
	board_id BIGINT NOT NULL,
    member_id VARCHAR(50) NOT NULL,
    comment_content TEXT NOT NULL,
	FOREIGN KEY (board_id) REFERENCES board (board_id) ON DELETE CASCADE,
	FOREIGN KEY (member_id) REFERENCES member (member_id) ON DELETE CASCADE
);

-- 화장품 종류 저장
INSERT INTO product_category (category) VALUES ('lipstick');
INSERT INTO product_category (category) VALUES ('fundation');
INSERT INTO product_category (category) VALUES ('eyeline');

-- 화장품 정보를 저장하기 위한 쿼리 
INSERT INTO product (product_name, category, product_link, Price) VALUES ('estee lauder bone', 'fundation', 'https://link.coupang.com/a/bZqekr',71930);
INSERT INTO product (product_name, category, product_link, Price) VALUES ('estee lauder natural swade', 'fundation', 'https://link.coupang.com/a/bZqeCM',71930);
INSERT INTO product (product_name, category, product_link, Price) VALUES ('estee lauder dun', 'fundation', 'https://link.coupang.com/a/bZqe2h', 71930);
INSERT INTO product (product_name, category, product_link, Price) VALUES ('estee lauder ecuru', 'fundation', 'https://link.coupang.com/a/bZqgrh', 71930);
INSERT INTO product (product_name, category, product_link, Price) VALUES ('estee lauder warm cream', 'fundation', 'https://link.coupang.com/a/bZqgI8', 71930);
INSERT INTO product (product_name, category, product_link, Price) VALUES ('locean vampire red', 'lipstick', 'https://link.coupang.com/a/bZqh1x', 5090);
INSERT INTO product (product_name, category, product_link, Price) VALUES ('locean red moon', 'lipstick', 'https://link.coupang.com/a/bZqit6', 5330);
INSERT INTO product (product_name, category, product_link, Price) VALUES ('locean rose blossom', 'lipstick', 'https://link.coupang.com/a/bZqkta', 5330);
INSERT INTO product (product_name, category, product_link, Price) VALUES ('locean dorothy red', 'lipstick', 'https://link.coupang.com/a/bZqjP3', 15810);
INSERT INTO product (product_name, category, product_link, Price) VALUES ('locean deep pink curten', 'lipstick', 'https://link.coupang.com/a/bZqkX3', 5900);

-- 이미지 테이블에 제품 이미지 링크 저장
INSERT INTO image (product_code, image_link) VALUES (1, '/productimg/esteelauderbone.jpg');
INSERT INTO image (product_code, image_link) VALUES (2, '/productimg/esteelaudernaturalswade.jpg');
INSERT INTO image (product_code, image_link) VALUES (3, '/productimg/esteelauderdun.jpg');
INSERT INTO image (product_code, image_link) VALUES (4, '/productimg/esteelauderecuru.jpg');
INSERT INTO image (product_code, image_link) VALUES (5, '/productimg/esteelauderwarmcream.jpg');
INSERT INTO image (product_code, image_link) VALUES (6, '/productimg/loceanvampirered.jpg');
INSERT INTO image (product_code, image_link) VALUES (7, '/productimg/loceanredmoon.jpg');
INSERT INTO image (product_code, image_link) VALUES (8, '/productimg/loceanroseblossom.jpg');
INSERT INTO image (product_code, image_link) VALUES (9, '/productimg/loceandorothyred.jpg');
INSERT INTO image (product_code, image_link) VALUES (10, '/productimg/loceandeeppinkcurten.jpg');


