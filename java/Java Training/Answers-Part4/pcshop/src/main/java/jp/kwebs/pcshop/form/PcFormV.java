package jp.kwebs.pcshop.form;

import java.util.List;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class PcFormV {
	private Long id;
	@NotBlank(message="CPUを記入してください")
	private String cpu;
	@NotNull(message="メモリを記入してください")
	@Max(value=128, message="128GB以内です")
	@Min(value=4, message="4GB以上です")
	private Integer memory;
	@NotNull(message="OSを選択してください")
	private String os;
	@NotEmpty(message="１つ以上選択してください")
	private List<String> storage;
	
	public PcFormV() {}
	public PcFormV(Long id, String cpu, Integer memory, String os, List<String> storage) {
		this.id = id;
		this.cpu = cpu;
		this.memory = memory;
		this.os = os;
		this.storage = storage;
	}
	public Long getId() {
		return id;
	}
	public String getCpu() {
		return cpu;
	}
	public Integer getMemory() {
		return memory;
	}
	public String getOs() {
		return os;
	}
	public List<String> getStorage() {
		return storage;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setCpu(String cpu) {
		this.cpu = cpu;
	}
	public void setMemory(Integer memory) {
		this.memory = memory;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public void setStorage(List<String> storage) {
		this.storage = storage;
	}
}
