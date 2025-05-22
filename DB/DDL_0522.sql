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
  `id` INT NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `authority` INT NOT NULL DEFAULT 3,
  `generation` INT NOT NULL,
  `affiliate` VARCHAR(10) NULL,
  `name` VARCHAR(45) NULL,
  `nickname` VARCHAR(45) NULL,
  `boj_id` VARCHAR(100) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
