CREATE TABLE `User` (
  `UserID` VARCHAR(20) PRIMARY KEY,
  `Password` VARCHAR(20) NOT NULL,
  `Email` VARCHAR(30) NOT NULL,
  `PhoneNumber` VARCHAR(13)
);

CREATE TABLE `Makeup` (
  `MakeupID` INT PRIMARY KEY,
  `UserID` VARCHAR(20),
  `ColorCode` VARCHAR(20) NOT NULL,  -- Assuming a hex color code
  `Opacity` INT NOT NULL,
  FOREIGN KEY (`UserID`) REFERENCES `User` (`UserID`) ON DELETE CASCADE
);

CREATE TABLE `ProductType` (
  `MakeupID` INT,
  `ProductCode` INT,
  `ProductTypeName` VARCHAR(20) NOT NULL,
  FOREIGN KEY (`MakeupID`) REFERENCES `Makeup` (`MakeupID`) ON DELETE CASCADE,
  FOREIGN KEY (`ProductCode`) REFERENCES `Product` (`ProductCode`) ON DELETE CASCADE
);

CREATE TABLE `Product` (
  `ProductCode` INT PRIMARY KEY,
  `ImageCode` INT,
  `ProductName` VARCHAR(20) NOT NULL,
  `Price DECIMAL(10, 2) NOT NULL,  -- Adjust precision as needed
  FOREIGN KEY (`ImageCode`) REFERENCES `ImageLink` (`ImageCode`) ON DELETE SET NULL
);

CREATE TABLE `Board` (
  `BoardID` INT PRIMARY KEY,
  `UserID` VARCHAR(20),
  `ImageCode` INT,
  `Title` VARCHAR(20) NOT NULL,
  `Content TEXT,
  FOREIGN KEY (`UserID`) REFERENCES `User` (`UserID`) ON DELETE CASCADE,
  FOREIGN KEY (`ImageCode`) REFERENCES `ImageLink` (`ImageCode`) ON DELETE SET NULL
);

CREATE TABLE `Comment` (
  `CommentID` INT PRIMARY KEY,
  `BoardID` INT,
  `UserID` VARCHAR(20),
  `CommentContent` TEXT NOT NULL,
  FOREIGN KEY (`BoardID`) REFERENCES `Board` (`BoardID`) ON DELETE CASCADE,
  FOREIGN KEY (`UserID`) REFERENCES `User` (`UserID`) ON DELETE CASCADE
);

CREATE TABLE `ImageLink` (
  `ImageCode` INT PRIMARY KEY,
  `ImageLink` VARCHAR(20) NOT NULL
);
