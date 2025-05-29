package com.scsac.app.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.scsac.app.dto.Opinion;
import com.scsac.app.dto.Problem;
import com.scsac.app.entity.OpinionEntity;
import com.scsac.app.entity.ProblemEntity;
import com.scsac.app.repository.OpinionRepository;
import com.scsac.app.repository.ProblemRepository;
import com.scsac.app.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProblemServiceImpl implements ProblemService {

	private final ProblemRepository pr;
	private final OpinionRepository or;
	private final UserRepository ur;

	@Override
	public List<Problem> selectAll() {
		List<Problem> problems = ProblemEntity.toDto(pr.findAll());
		if (problems == null) {
			return null;
		}
		for (Problem problem : problems) {
			List<OpinionEntity> e = or.findByProblemId(problem.getId());
			List<Opinion> opinions = OpinionEntity.toDto(e);
			Set<String> categories = new HashSet<>();
			for (Opinion opinion : opinions) {
				for (String category : opinion.getCategory()) {
					categories.add(category);
				}
			}
			List<String> categoriesList = new ArrayList<>(categories);
			categoriesList.sort(null);
			problem.setCategories(categoriesList);
		}
		return problems;
	}

	@Override
	public Page<Problem> selectPagedProblems(Pageable pageable) {
	    Page<ProblemEntity> entities = pr.findAll(pageable);

	    return entities.map(entity -> {
	        Problem problem = ProblemEntity.toDto(entity);

	        // 1. 해당 문제에 연결된 OpinionEntity 목록 가져오기
	        List<OpinionEntity> e = or.findByProblemId(problem.getId());
	        List<Opinion> opinions = OpinionEntity.toDto(e);

	        // 2. 카테고리 추출
	        Set<String> categories = new HashSet<>();
	        for (Opinion opinion : opinions) {
	            for (String category : opinion.getCategory()) {
	                categories.add(category);
	            }
	        }

	        List<String> categoriesList = new ArrayList<>(categories);
	        categoriesList.sort(null);
	        problem.setCategories(categoriesList);

	        return problem;
	    });
	}

	
	


	@Override
	public Problem selectById(int id) {
		Optional<ProblemEntity> pe = pr.findById(id);
		if(pe.isEmpty()) return null;
		Problem problem = ProblemEntity.toDto(pe.get());
		List<OpinionEntity> e = or.findByProblemId(problem.getId());
		List<Opinion> opinions = OpinionEntity.toDto(e);
		Set<String> categories = new HashSet<>();
		for (Opinion opinion : opinions) {
			for (String category : opinion.getCategory()) {
				categories.add(category);
			}
		}
		problem.setOpinions(opinions);
		List<String> categoriesList = new ArrayList<>(categories);
		categoriesList.sort(null);
		problem.setCategories(categoriesList);
		return problem;
	}
	@Override
	public Page<Problem> searchPagedProblems(String condition, String value, Pageable pageable) {
	    Page<ProblemEntity> problemsPage;

	    try {
	        switch (condition) {
	            case "problemNum":
	                int problemNum = Integer.parseInt(value);
	                problemsPage = pr.findByProblemNum(problemNum, pageable);
	                break;
	            case "title":
	                problemsPage = pr.findByTitleContaining(value, pageable);
	                break;
	            case "rate":
	                int rate = Integer.parseInt(value);
	                problemsPage = pr.findByRateGreaterThanEqual(rate, pageable);
	                break;
	            case "category":
	                problemsPage = pr.findProblemsByCategoryName(value, pageable);
	                break;
	            default:
	                return Page.empty(); // 지원하지 않는 검색 조건
	        }
	    } catch (Exception e) {
	        // 기본 전체 조회
	        List<Problem> all = selectAll(); // 전체 조회
	        int start = (int) pageable.getOffset();
	        int end = Math.min(start + pageable.getPageSize(), all.size());
	        List<Problem> sublist = all.subList(start, end);
	        return new PageImpl<>(sublist, pageable, all.size());
	    }

	    // DTO 변환 후 카테고리 수동 추가
	    List<Problem> problemDtoList = ProblemEntity.toDto(problemsPage.getContent());
	    for (Problem problem : problemDtoList) {
	        List<OpinionEntity> e = or.findByProblemId(problem.getId());
	        List<Opinion> opinions = OpinionEntity.toDto(e); 
	        Set<String> categorySet = new HashSet<>();
	        for (Opinion opinion : opinions) {
	            categorySet.addAll(opinion.getCategory());
	        }
	        List<String> sortedCategories = new ArrayList<>(categorySet);
	        Collections.sort(sortedCategories);
	        problem.setCategories(sortedCategories);
	    }

	    return new PageImpl<>(problemDtoList, pageable, problemsPage.getTotalElements());
	}

	@Override
	@Transactional
	public int insertProblem(Problem problem) {
		ProblemEntity e = pr.save(problem.toEntity(problem));
		ProblemEntity saved = pr.save(e);
	    
	    problem.setId(saved.getId()); 

		return 1;
	}

	@Override
	@Transactional
	public int deleteProblem(int id) {
		try {
			pr.deleteById(id);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	@Transactional
	public int updateProblem(Problem problem) {
		Optional<ProblemEntity> pe = pr.findById(problem.getId());
		if(pe.isEmpty()) return 0;
		ProblemEntity e = pe.get();
		
		e.setProblemNum(problem.getProblemNum());
		e.setTitle(problem.getTitle());
		e.setUrl(problem.getUrl());
		
		return 1;
	}
	
	@Override
	@Transactional
	public int updateProblemRate(Problem problem) {
	    List<OpinionEntity> e = or.findByProblemId(problem.getId());
	    List<Opinion> opinions = OpinionEntity.toDto(e);

	    int cnt = 0;
	    int sum = 0;

	    for (Opinion opinion : opinions) {
	        cnt += 1;
	        sum += opinion.getRate();
	    }

	    if (cnt == 0) {
	        return 0;
	    } else {
	    	BigDecimal avg = BigDecimal.valueOf((float) sum / cnt);
	    	avg = avg.setScale(2, RoundingMode.HALF_UP);
	    	problem.setRate(avg.floatValue());
	    }
	    Optional<ProblemEntity> pe = pr.findById(problem.getId());
		if(pe.isPresent()) return 0;
	    ProblemEntity entity = pe.get();
	    entity.setRate(problem.getRate());
	    pr.save(entity);

	    return 1;
	}

}
