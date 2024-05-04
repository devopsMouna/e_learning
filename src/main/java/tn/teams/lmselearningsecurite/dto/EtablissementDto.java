package tn.teams.lmselearningsecurite.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.teams.lmselearningsecurite.entites.Etablissement;
import tn.teams.lmselearningsecurite.entites.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EtablissementDto {
	private Long  idEtablissement;
	private String typeEtablissement;
	
	
	public static Etablissement toEntity(EtablissementDto request)
	{
	return Etablissement.builder()
			.typeEtablissement(request.typeEtablissement)
			
			.build();

		
		
	}
	
	
	public static EtablissementDto fromEntity(Etablissement request)
	{
	return EtablissementDto.builder()
			.typeEtablissement(request.getTypeEtablissement())
			
			.build();
		
		
		
	}
}
