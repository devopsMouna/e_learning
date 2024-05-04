package tn.teams.lmselearningsecurite.services;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tn.teams.lmselearningsecurite.dto.EtablissementDto;
import tn.teams.lmselearningsecurite.entites.Etablissement;
import tn.teams.lmselearningsecurite.repository.EtablissementRepository;
@Service
@RequiredArgsConstructor
public class EtablissementServiceimpl implements EtablissementService {
private final EtablissementRepository etablissementRepository;
	@Override
	public List<EtablissementDto> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EtablissementDto save(EtablissementDto entity) {
		Etablissement etablissement=EtablissementDto.toEntity(entity);
		Etablissement etablissementsaved=etablissementRepository.save(etablissement);
		EtablissementDto etablissementDto=EtablissementDto.fromEntity(etablissementsaved);
		return etablissementDto;
	}

	@Override
	public EtablissementDto findById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EtablissementDto findByKeyWord(String keyw) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EtablissementDto> findListByKeyWord(String keyw) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EtablissementDto update(EtablissementDto entity, long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(EtablissementDto entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

}
