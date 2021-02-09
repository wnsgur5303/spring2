package kr.or.ddit.batch.yogurt;

import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

import java.util.List;

import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;

public class CustomMyBatisBatchItemWriter<T> implements ItemWriter<List<T>>, InitializingBean {

	  private static final Logger LOGGER = LoggerFactory.getLogger(CustomMyBatisBatchItemWriter.class);

	  private SqlSessionTemplate sqlSessionTemplate;

	  private String statementId;

	  private boolean assertUpdates = true;

	  private Converter<T, ?> itemToParameterConverter = new PassThroughConverter<>();


	  public void setAssertUpdates(boolean assertUpdates) {
	    this.assertUpdates = assertUpdates;
	  }


	  public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
	    if (sqlSessionTemplate == null) {
	      this.sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
	    }
	  }


	  public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
	    this.sqlSessionTemplate = sqlSessionTemplate;
	  }

	  public void setStatementId(String statementId) {
	    this.statementId = statementId;
	  }

	
	  public void setItemToParameterConverter(Converter<T, ?> itemToParameterConverter) {
	    this.itemToParameterConverter = itemToParameterConverter;
	  }

	  @Override
	  public void afterPropertiesSet() {
	    notNull(sqlSessionTemplate, "A SqlSessionFactory or a SqlSessionTemplate is required.");
	    isTrue(ExecutorType.BATCH == sqlSessionTemplate.getExecutorType(),
	        "SqlSessionTemplate's executor type must be BATCH");
	    notNull(statementId, "A statementId is required.");
	    notNull(itemToParameterConverter, "A itemToParameterConverter is required.");
	  }

	  /**
	   * {@inheritDoc}
	   */
	  @Override
	  public void write(final List<? extends List<T>> items) {

	    if (!items.isEmpty()) {
	      LOGGER.debug(() -> "Executing batch with " + items.size() + " items.");

	      //item : List<List<DaileVi>>
	      for (List<T> list : items) {
	    	  for(T item : list) {
	        sqlSessionTemplate.update(statementId, itemToParameterConverter.convert(item));
	    		}
	      }

	      List<BatchResult> results = sqlSessionTemplate.flushStatements();

	      if (assertUpdates) {
	        if (results.size() <= 0) {
	          throw new InvalidDataAccessResourceUsageException("Batch execution returned invalid results. "
	              + "Expected 1 but number of BatchResult objects returned was " + results.size());
	        }

	        int[] updateCounts = results.get(0).getUpdateCounts();

	        for (int i = 0; i < updateCounts.length; i++) {
	          int value = updateCounts[i];
	          if (value == 0) {
	            throw new EmptyResultDataAccessException(
	                "Item " + i + " of " + updateCounts.length + " did not update any rows: [" + items.get(i) + "]", 1);
	          }
	        }
	      }
	    }
	  }

	  private static class PassThroughConverter<T> implements Converter<T, T> {

	    @Override
	    public T convert(T source) {
	      return source;
	    }

	  }

	}
