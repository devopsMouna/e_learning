package tn.teams.lmselearningsecurite.services;

import java.util.List;

public interface GenrericInterfaceService<T> {
	List<T> findAll();

	T save(T entity);

	T findById(long id);

	void deleteById(long id);

	T findByKeyWord(String keyw);

	List<T> findListByKeyWord(String keyw);

	T update(T entity, long id);

	void delete(T entity);

	long count();
}
