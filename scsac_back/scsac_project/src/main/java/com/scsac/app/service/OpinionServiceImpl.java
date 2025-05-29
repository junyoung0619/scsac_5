package com.scsac.app.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.scsac.app.dto.Opinion;
import com.scsac.app.entity.CategoryEntity;
import com.scsac.app.entity.FeedbackCategoryEntity;
import com.scsac.app.entity.OpinionEntity;
import com.scsac.app.entity.ProblemEntity;
import com.scsac.app.entity.UserEntity;
import com.scsac.app.repository.CategoryRepository;
import com.scsac.app.repository.FeedbackCategoryRepository;
import com.scsac.app.repository.OpinionRepository;
import com.scsac.app.repository.ProblemRepository;
import com.scsac.app.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OpinionServiceImpl implements OpinionService {

	private final OpinionRepository or;
	private final ProblemRepository pr;
	private final UserRepository ur;
	private final CategoryRepository cr;
	private final FeedbackCategoryRepository fcr;

	@Override
	public List<Opinion> findByProblemId(int problemId) {
		List<OpinionEntity> opinions = or.findByProblemId(problemId);
		return OpinionEntity.toDto(opinions);
	}

	@Override
	public Optional<Integer> getProblemIdByOpinionId(int opinionId) {
		return or.findProblemIdByOpinionId(opinionId);
	}

	@Override
	@Transactional
	public int insertOpinion(Opinion opinion) {
		Optional<ProblemEntity> pe = pr.findById(opinion.getProblemId());
		if (pe.isEmpty())
			return 0;
		ProblemEntity problem = pe.get();

		UserEntity user = ur.findById(opinion.getUserId()).orElse(null);
		if (user == null)
			return 0;

		Set<CategoryEntity> categories = opinion.getCategory().stream().map(cr::findByName).flatMap(Optional::stream)
				.collect(Collectors.toSet());

		Set<FeedbackCategoryEntity> feedbackCategories = opinion.getFeedbackCategory().stream().map(fcr::findByName)
				.flatMap(Optional::stream).collect(Collectors.toSet());
	
		OpinionEntity entity = Opinion.toEntity(opinion, problem, user, categories, feedbackCategories);

		return or.save(entity) != null ? 1 : 0;
	}

	@Override
	@Transactional
	public int updateOpinion(Opinion opinion) {

		Optional<OpinionEntity> oe = or.findById(opinion.getId());
		if (oe.isEmpty())
			return 0;

		OpinionEntity e = oe.get();

		Set<CategoryEntity> categories = opinion.getCategory().stream().map(cr::findByName).flatMap(Optional::stream)
				.collect(Collectors.toSet());
		e.setCategories(categories);

		Set<FeedbackCategoryEntity> feedbackCategories = opinion.getFeedbackCategory().stream().map(fcr::findByName)
				.flatMap(Optional::stream).collect(Collectors.toSet());
		e.setFeedbackCategories(feedbackCategories);

		e.setRate(opinion.getRate());
		e.setComment(opinion.getComment());
		return 1;
	}

	@Override
	@Transactional
	public int deleteOpinion(int id) {
		try {
			or.deleteById(id);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

}
