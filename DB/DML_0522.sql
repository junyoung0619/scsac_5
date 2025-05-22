-- 비밀번호는 전부 1234 입니다
-- 각자 개인 학번으로 관리자 번호 만들어두었습니다
-- 2400 : 정보 있음 / 시험 전
-- 2401 : 정보 있음 / 시험 후
-- 2500 : 정보 없음 / 시험 전

INSERT INTO `scsac`.`user` (`id`, `password`, `authority`, `generation`, `affiliate`, `name`, `nickname`, `boj_id`) VALUES ('2406', '$2a$12$4Ve5zKH2eFOp4fGHAvv8Q.6qdFZRqsgYKqvuhKacsF0LHU2hWDC56', '1', '24', 'DX', '김혜민', 'hyenem', 'hyenem');
INSERT INTO `scsac`.`user` (`id`, `password`, `authority`, `generation`, `affiliate`, `name`, `nickname`, `boj_id`) VALUES ('2402', '$2a$12$4Ve5zKH2eFOp4fGHAvv8Q.6qdFZRqsgYKqvuhKacsF0LHU2hWDC56', '1', '24', 'DX', '김승기', 'testfirst', 'testfirst');
INSERT INTO `scsac`.`user` (`id`, `password`, `authority`, `generation`, `affiliate`, `name`, `nickname`, `boj_id`) VALUES ('2405', '$2a$12$4Ve5zKH2eFOp4fGHAvv8Q.6qdFZRqsgYKqvuhKacsF0LHU2hWDC56', '1', '24', 'SDS', '김정우', 'jungwoo0405', 'jungwoo0405');
INSERT INTO `scsac`.`user` (`id`, `password`, `authority`, `generation`, `affiliate`, `name`, `nickname`, `boj_id`) VALUES ('2411', '$2a$12$4Ve5zKH2eFOp4fGHAvv8Q.6qdFZRqsgYKqvuhKacsF0LHU2hWDC56', '1', '24', 'DX', '이준영', 'jylee0619', 'jylee0619');
INSERT INTO `scsac`.`user` (`id`, `password`, `authority`, `generation`, `affiliate`, `name`, `nickname`, `boj_id`) VALUES ('2400', '$2a$12$4Ve5zKH2eFOp4fGHAvv8Q.6qdFZRqsgYKqvuhKacsF0LHU2hWDC56', '3', '25', 'SDS', '신사임당', '50000', '50000');
INSERT INTO `scsac`.`user` (`id`, `password`, `authority`, `generation`, `affiliate`, `name`, `nickname`) VALUES ('2401', '$2a$12$4Ve5zKH2eFOp4fGHAvv8Q.6qdFZRqsgYKqvuhKacsF0LHU2hWDC56', '2', '25', 'DS', '홍길동', 'cantcallfather');
INSERT INTO `scsac`.`user` (`id`, `password`, `authority`, `generation`) VALUES ('2500', '$2a$12$4Ve5zKH2eFOp4fGHAvv8Q.6qdFZRqsgYKqvuhKacsF0LHU2hWDC56', '3', '5');
