-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema scsac
-- -----------------------------------------------------useruser

-- -----------------------------------------------------
-- Schema scsac
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `scsac` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema scsac
-- -----------------------------------------------------
USE `scsac` ;

-- -----------------------------------------------------
-- Table `scsac`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scsac`.`user` (
  `id` varchar(10) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `authority` INT NOT NULL DEFAULT 3,
  `generation` INT NOT NULL,
  `affiliate` VARCHAR(10) NULL,
  `name` VARCHAR(45) NULL,
  `nickname` VARCHAR(45) NULL,
  `boj_id` VARCHAR(100) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;
-- -----------------------------------------------------
-- Table `scsac`.`problem`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS problem (
  id INT AUTO_INCREMENT PRIMARY KEY,
  url VARCHAR(50) NOT NULL,
  problem_num INT NOT NULL,
  title VARCHAR(50) NOT NULL,
  rate INT NOT NULL
) ENGINE=InnoDB;
-- -----------------------------------------------------
-- Table `scsac`.`opinion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS opinion (
  id INT AUTO_INCREMENT PRIMARY KEY,
  problem_id INT,
  rate INT NOT NULL,
  comment TEXT,
  FOREIGN KEY (problem_id) REFERENCES problem(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- -----------------------------------------------------
-- Table `scsac`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS category (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB;

-- -----------------------------------------------------
-- Table `scsac`.`feedback_category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS feedback_category (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB;

-- -----------------------------------------------------
-- Table `scsac`.`opinion_category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS opinion_category (
  opinion_id INT,
  category_id INT,
  PRIMARY KEY (opinion_id, category_id),
  FOREIGN KEY (opinion_id) REFERENCES opinion(id) ON DELETE CASCADE,
  FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- -----------------------------------------------------
-- Table `scsac`.`opinion_feedback_category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS opinion_feedback_category (
  opinion_id INT,
  feedback_category_id INT,
  PRIMARY KEY (opinion_id, feedback_category_id),
  FOREIGN KEY (opinion_id) REFERENCES opinion(id) ON DELETE CASCADE,
  FOREIGN KEY (feedback_category_id) REFERENCES feedback_category(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- -----------------------------------------------------

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
