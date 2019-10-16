package cc.zenking.edu.zhjx.enty;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/27.
 */

public class ExamList implements Serializable {
    public float percentOverClass;
    public float total;
    public float percentOverAll;
    public float classAverage;
    public ExamScore[] scores;
    public float classHighestScore;
    public RankingGraph[] rankingGraphs;
    public String examName;
}
