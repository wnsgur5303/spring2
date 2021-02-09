package kr.or.ddit.batch.yogurt;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

import javafx.scene.chart.PieChart.Data;
import kr.or.ddit.yogurt.model.CycleVo;
import kr.or.ddit.yogurt.model.DailyVo;

public class YogurtProcessor implements ItemProcessor<CycleVo, List<DailyVo>>{
	private static final Logger logger = LoggerFactory.getLogger(YogurtProcessor.class);

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	
	@Value("#{jobParameters[dt]}")//이게 표기방법임. 파라미터를 여기에 주입을 하겠다.
	private Date dt;
	//문자로 받는게 더 편할 것 같으면 문자로.
	
	
	//dt : 202102, item : cid-1,pid-100, day-2, cnt-1
	//==>
	//					  cid-1,pid-100,dt-20210201,cnt-1
	//					  cid-1,pid-100,dt-20210208,cnt-1
	//					  cid-1,pid-100,dt-20210215,cnt-1
	//					  cid-1,pid-100,dt-20210222,cnt-1
	//해당년월에 해당하는 1~마지막 날까지 루프를 도는
	
	//1일부터 28일까지 loop
	//if(요일 = item.요일과 같은지 체크)
	//		해당 일자로 일시적 데이터를 생성
	//
	
	//new Date
	//해달년월의 마지막 날짜(date)
	//해당년월의 몇번째 날짜 - 1일(date)
	@Override
	public List<DailyVo> process(CycleVo item) throws Exception {
		//현재날짜시간
		Calendar calender = Calendar.getInstance();
		
		calender.setTime(dt);
		calender.set(calender.DAY_OF_MONTH,calender.getActualMaximum(calender.DAY_OF_MONTH));
		
		// 20210201 02:00:00
		
		Date endDt = calender.getTime();
		
		calender.set(Calendar.DAY_OF_MONTH,1);
		calender.set(Calendar.HOUR_OF_DAY, 0);
		
		// 20210201 00:00:00.5?
		//Date startDt = calender.getTime();
		
		
		List<DailyVo> dailVoList = new ArrayList<DailyVo>();
		while(endDt.compareTo(calender.getTime())>0) {
			
			//20210201 ==> 주간요일
			if(item.getDay() == calender.get(Calendar.DAY_OF_WEEK)) {
				
				//cid, pid, dt(yyyyMMdd), cnt
				
				dailVoList.add(new DailyVo
								(item.getCid()
								,item.getPid()
								,sdf.format(calender.getTime())
								,item.getCnt()));
				
				
				
			}
			
			
			calender.set(Calendar.DAY_OF_MONTH, calender.get(Calendar.DAY_OF_MONTH)+1);
			
			
		}
		
		return dailVoList;
	}
	

	

}
