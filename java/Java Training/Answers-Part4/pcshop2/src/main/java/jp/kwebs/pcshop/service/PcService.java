package jp.kwebs.pcshop.service;

import java.util.List;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import jp.kwebs.pcshop.entiry.Pc;
import jp.kwebs.pcshop.form.PcForm;
import jp.kwebs.pcshop.repository.PcRepository;

@Service
public class PcService {

	private final PcRepository repo;

	public PcService(PcRepository repo) {
		this.repo = repo;
	}
	
	public List<Pc> readAllPcs() {
		return repo.findAll();
	}
	
	public void createAllPcs(List<Pc> pcs) {
		repo.saveAll(pcs);
	}
	
	public Pc readPcById(Long id) {
		return repo.findById(id).orElseThrow();
	}
	
	@Transactional
	public void createPc(PcForm pcForm) {
		Pc pc = new Pc();
		toEntity(pc, pcForm);
		repo.save(pc);
	}
	
	@Transactional
	public Pc updatePc(PcForm pcForm) {
		Pc pc = readPcById(pcForm.getId());
		toEntity(pc, pcForm);
		repo.save(pc);
		return pc;
	}
	
	@Transactional
	public void deletePc(Long id) {
		repo.deleteById(id);
	}
	
	public void toEntity(Pc pc, PcForm pcForm) {
		pc.setCpu(pcForm.getCpu());
		pc.setMemory(pcForm.getMemory());
		pc.setOs(pcForm.getOs());
		pc.setStorage(pcForm.getStorage());
		
	}

	public PcForm toForm(Pc pc) {
		return new PcForm(pc.getId(), pc.getCpu(), pc.getMemory(), pc.getOs(), pc.getStorage());
	}


}


















