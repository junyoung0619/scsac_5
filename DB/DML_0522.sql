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

-- Table `scsac`.`problem`
INSERT INTO problem (url, problem_num, title, rate) VALUES
('https://www.acmicpc.net/problem/1000', 1000, 'A+B', 4),
('https://www.acmicpc.net/problem/1010', 1010, '다리 놓기', 5),
('https://www.acmicpc.net/problem/10828', 10828, '스택', 3);

-- Table `scsac`.`opinion`
INSERT INTO opinion (problem_id, rate, comment) VALUES
(1, 5, '문제 설명이 친절하고 입문자에게 좋았어요.'),
(2, 3, '조합 개념을 잘 알고 풀어야 했습니다.'),
(3, 4, '스택 동작을 직접 구현해보는 재미가 있었습니다.');

-- Table `scsac`.`category`
INSERT INTO category (name) VALUES
('구현'),
('백트래킹'),
('시뮬레이션'),
('너비 우선 탐색'),
('깊이 우선 탐색'),
('기타') ;

-- Table `scsac`.`feedback_category`
INSERT INTO feedback_category (name) VALUES
('구현 연습하기 좋아요'),
('bfs 연습에 좋아요'),
('문자열 처리에 익숙해질 수 있음'),
('엣지 케이스 다수');

-- Table `scsac`.`opinion_category`
-- opinion 1: 구현
INSERT INTO opinion_category (opinion_id, category_id) VALUES
(1, 1);

-- opinion 2: 백트래킹, 시뮬레이션
INSERT INTO opinion_category (opinion_id, category_id) VALUES
(2, 2),
(2, 3);

-- opinion 3: 너비 우선 탐색, 구현
INSERT INTO opinion_category (opinion_id, category_id) VALUES
(3, 4),
(3, 1);

-- Table `scsac`.`opinion_feedback_category`
-- opinion 1
INSERT INTO opinion_feedback_category (opinion_id, feedback_category_id) VALUES
(1, 3);

-- opinion 2
INSERT INTO opinion_feedback_category (opinion_id, feedback_category_id) VALUES
(2, 1),
(2, 4);

-- opinion 3
INSERT INTO opinion_feedback_category (opinion_id, feedback_category_id) VALUES
(3, 2);

