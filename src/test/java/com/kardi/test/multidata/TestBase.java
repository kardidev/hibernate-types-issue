package com.kardi.test.multidata;

import com.kardi.test.multidata.entities.ProblemTypeEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public abstract class TestBase {

    private static final int DATA_SIZE = 100000;
    private static final String BIG_STRING = generateString(DATA_SIZE);
    private static final byte[] BIG_BINARY = new byte[DATA_SIZE];

    private static AtomicLong nextIndex = new AtomicLong(0);

    @Autowired
    private LocalContainerEntityManagerFactoryBean entityManagerFactory;

    private EntityManager entityManager;

    @Before
    public void setUp() throws Exception {
        entityManager = entityManagerFactory.getObject().createEntityManager();
    }

    @Test
    public void testSmallData() {
        testData(nextIndex.incrementAndGet(), "111", new byte[10]);
    }

    @Test
    public void testNullValues() {
        testData(nextIndex.incrementAndGet(), null, null);
    }

    @Test
    public void testEmptyValues() {
        testData(nextIndex.incrementAndGet(), "", new byte[0]);
    }

    @Test
    public void testBigString() {
        testData(nextIndex.incrementAndGet(), BIG_STRING, new byte[10]);
    }

    @Test
    public void testBigBinary() {
        testData(nextIndex.incrementAndGet(), "111", BIG_BINARY);
    }

    @Test
    public void testAllFields() {
        testData(nextIndex.incrementAndGet(),
                BIG_STRING,
                generateString(9),
                generateString(269),
                BIG_STRING,
                BIG_BINARY,
                new byte[9],
                new byte[269],
                BIG_BINARY);
    }

    @Test
    public void testDateOperations() {

        long alertEntityIndex = prepareDateOperationsEntities();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, Calendar.MARCH, 2);

        // JPQL over hibernate (HQL)
        Query query = entityManager.createQuery("from ProblemTypeEntity e where addmonths(e.controlDate, e.months) > :plimit");
        query.setParameter("plimit", calendar.getTime());
        List result = query.getResultList();

        assert(result != null);
        assert(result.size() == 1);
        assert(((ProblemTypeEntity)result.get(0)).getId() == alertEntityIndex);

        // JPA criteria
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProblemTypeEntity> cQuery = cb.createQuery(ProblemTypeEntity.class);
        Root root = cQuery.from(ProblemTypeEntity.class);
        Expression<Date> function = cb.function("addmonths", Date.class, root.get("controlDate"), root.get("months"));
        cQuery.where(cb.greaterThan(function, calendar.getTime()));

        result = entityManager.createQuery(cQuery).getResultList();

        assert(result != null);
        assert(result.size() == 1);
        assert(((ProblemTypeEntity)result.get(0)).getId() == alertEntityIndex);
    }

    @After
    public void tearDown() throws Exception {
        if (entityManager != null) {
            entityManager.close();
        }
    }

    private void testData(long id, String text, byte[] binary) {
        testData(id, text, null, null, null, binary, null, null, null);
    }

    private void testData(long id,
                            String text,
                            String textSmall,
                            String textMedium,
                            String textLarge,
                            byte[] binary,
                            byte[] binarySmall,
                            byte[] binaryMedium,
                            byte[] binaryLarge) {

        ProblemTypeEntity problemTypeEntity = new ProblemTypeEntity();
        problemTypeEntity.setId(id);
        problemTypeEntity.setBinary_field(binary);
        problemTypeEntity.setText_field(text);
        problemTypeEntity.setLimited_string_1(textSmall);
        problemTypeEntity.setLimited_string_2(textMedium);
        problemTypeEntity.setLimited_string_3(textLarge);
        problemTypeEntity.setLimited_binary_1(binarySmall);
        problemTypeEntity.setLimited_binary_2(binaryMedium);
        problemTypeEntity.setLimited_binary_3(binaryLarge);


        EntityTransaction tr = entityManager.getTransaction();
        tr.begin();
        entityManager.persist(problemTypeEntity);
        tr.commit();

        entityManager.refresh(problemTypeEntity);
        ProblemTypeEntity entity = entityManager.find(ProblemTypeEntity.class, id);

        assert (entity != null);
        if (text != null) {
            assert (entity.getText_field().length() == text.length());
        } else {
            assert (entity.getText_field() == null);
        }
        if (binary != null) {
            assert (entity.getBinary_field().length == binary.length);
        } else {
            assert (entity.getBinary_field() == null);
        }

        if (textSmall != null) {
            assert (entity.getLimited_string_1().length() == textSmall.length());
        } else {
            assert (entity.getLimited_string_1() == null);
        }
        if (textMedium != null) {
            assert (entity.getLimited_string_2().length() == textMedium.length());
        } else {
            assert (entity.getLimited_string_2() == null);
        }
        if (textLarge != null) {
            assert (entity.getLimited_string_3().length() == textLarge.length());
        } else {
            assert (entity.getLimited_string_3() == null);
        }
        if (binarySmall != null) {
            assert (entity.getLimited_binary_1().length == binarySmall.length);
        } else {
            assert (entity.getLimited_binary_1() == null);
        }
        if (binaryMedium != null) {
            assert (entity.getLimited_binary_2().length == binaryMedium.length);
        } else {
            assert (entity.getLimited_binary_2() == null);
        }
        if (binaryLarge != null) {
            assert (entity.getLimited_binary_3().length == binaryLarge.length);
        } else {
            assert (entity.getLimited_binary_3() == null);
        }
    }

    private long prepareDateOperationsEntities() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, Calendar.FEBRUARY, 1);

        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();

        ProblemTypeEntity entity1 = new ProblemTypeEntity();
        entity1.setId(nextIndex.incrementAndGet());
        entity1.setControlDate(calendar.getTime());
        entity1.setMonths(1);
        entityManager.persist(entity1);

        long alertEntityIndex = nextIndex.incrementAndGet();
        ProblemTypeEntity entity2 = new ProblemTypeEntity();
        entity2.setId(alertEntityIndex);
        entity2.setControlDate(calendar.getTime());
        entity2.setMonths(2);
        entityManager.persist(entity2);

        tx.commit();
        return alertEntityIndex;
    }

    /**
     * Generates a string.
     *
     * @param size size of the string
     * @return string
     */
    private static String generateString(int size) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append("1");
        }
        return sb.toString();
    }
}