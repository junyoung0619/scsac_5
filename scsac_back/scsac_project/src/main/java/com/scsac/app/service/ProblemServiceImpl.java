package com.scsac.app.service;

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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProblemServiceImpl implements ProblemService {

	private final ProblemRepository pr;
	private final OpinionRespository or;

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
			problem.setCategories(new ArrayList<>(categories));
		}
		return problems;
	}

	@Override
	public List<Problem> selectBySearchcondition(String condition, String value) {
		List<ProblemEntity> problems = new ArrayList<>();
		switch (condition) {
		case ("problem_num"):
			problems = pr.findByProblemNum(Integer.parseInt(value));
			break;
		case ("title"):
			problems = pr.findByTitleContaining(value);
			break;
		case ("rate"):
			problems = pr.findByRateGreaterThanEqual(Integer.parseInt(value));
			break;
		default:
			problems = null;
		}
		if (problems == null)
			return null;
		List<Problem> ans = ProblemEntity.toDto(problems);
		return ans;
	}

	@Override
	public int insertProblem(Problem problem) {
		ProblemEntity e = pr.save(problem.toDto(problem));
		if (e == null) {
			return 0;
		}
		return 1;
	}

	@Override
	public int deleteProblem(int id) {
		try {
			pr.deleteById(id);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
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
	        problem.setRate((float) sum / cnt);
	    }

	    ProblemEntity entity = pr.findById(problem.getId());
	    entity.setRate(problem.getRate());
	    pr.save(entity);

	    return 1;
	}


}
