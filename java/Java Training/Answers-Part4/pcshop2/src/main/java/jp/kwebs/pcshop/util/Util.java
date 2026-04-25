package jp.kwebs.pcshop.util;

import java.util.List;

import jp.kwebs.pcshop.entiry.Pc;

public class Util {

	public static List<Pc> getPcs() {
        var list = List.of(
        		new Pc(null, "Core i7", 16, "Windows", List.of("HDD","STA-SSD")),
        		new Pc(null, "Core i5", 8, "Windows", List.of("STA-SSD")),
        		new Pc(null, "M3", 4, "MacOS", List.of("STA-SSD")),
        		new Pc(null, "Core i9", 32, "Windows", List.of("HDD", "STA-SSD")),
        		new Pc(null, "Core i7", 16, "Windows", List.of("HDD", "STA-SSD")),
        		new Pc(null, "M3", 8, "MacOS", List.of("STA-SSD")),
        		new Pc(null, "M3", 4, "MacOS", List.of("HDD")),
        		new Pc(null, "Core i9", 32, "Windows", List.of("HDD", "STA-SSD", "NVMe-SSD")),
        		new Pc(null, "M2", 16, "MacOS", List.of("HDD")),
        		new Pc(null, "Core i5", 8, "Linux", List.of("STA-SSD")),
        		new Pc(null, "Core i3", 4, "Windows", List.of("HDD")),
        		new Pc(null, "Core i9", 32, "Windows", List.of("HDD", "STA-SSD")),
        		new Pc(null, "Core i7", 16, "Windows", List.of("HDD", "STA-SSD")),
        		new Pc(null, "M2", 8, "MacOS", List.of("STA-SSD")),
        		new Pc(null, "Core i3", 4, "Windows", List.of("STA-SSD")) );
        			
        return list;
    }
}
