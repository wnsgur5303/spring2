package kr.or.ddit.batch.ranger;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

public class RangerWriter implements ItemWriter<String>{
private static final Logger logger = LoggerFactory.getLogger(RangerWriter.class);
	@Override
	public void write(List<? extends String> items) throws Exception {
						//t를 구현한 어떤 클래스든 상관없다
		for(String Item : items) {
			logger.debug("Writer : {}",Item);
		}
	}
	
}
