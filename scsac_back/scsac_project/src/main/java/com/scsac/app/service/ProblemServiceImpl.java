package com.scsac.app.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.scsac.app.dto.Opinion;
import com.scsac.app.dto.Problem;
import com.scsac.app.entity.OpinionEntity;
import com.scsac.app.entity.ProblemEntity;
import com.scsac.app.repository.OpinionRespository;
import com.scsac.app.repository.ProblemRepository;
import com.scsac.app.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProblemServiceImpl implements ProblemService {

	private final ProblemRepository pr;
	private final OpinionRespository or;
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
	public Problem selectById(int id) {
		ProblemEntity pe = pr.findById(id);
		if(pe==null) return null;
		Problem problem = ProblemEntity.toDto(pe);
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
	public List<Problem> selectBySearchcondition(String condition, String value) {
		List<ProblemEntity> problems = new ArrayList<>();
		try {
			System.out.println(condition+value);
			switch (condition) {
				case ("problemNum"):
					System.out.println(Integer.parseInt(value));
					problems = pr.findByProblemNum(Integer.parseInt(value));
					break;
				case ("title"):
					problems = pr.findByTitleContaining(value);
					break;
				case ("rate"):
					problems = pr.findByRateGreaterThanEqual(Integer.parseInt(value));
					break;
				case ("category"):
					problems = pr.findProblemsByCategoryName(value);
					break;
				default:
					problems = null;
			} 
			if (problems == null)
				return null;
		}catch (Exception e){
			return selectAll();
		}
		
		List<Problem> ans = ProblemEntity.toDto(problems);
		for (Problem problem : ans) {
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
		
		return ans;
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
		ProblemEntity e = pr.findById(problem.getId());
		if(e==null) return 0;
		
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
	    ProblemEntity entity = pr.findById(problem.getId());
	    entity.setRate(problem.getRate());
	    pr.save(entity);

	    return 1;
	}

}
