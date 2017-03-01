package kartina.card.ViewType;

import kartina.R;
import kartina.card.bean.CardBean1000;
import kartina.card.bean.CardBean1001;
import kartina.card.bean.CardBean1004;
import kartina.card.bean.CardBean1005;
import kartina.card.bean.CardBean1006;
import kartina.card.bean.CardBean1008;
import kartina.card.bean.CardBean1010;
import kartina.card.bean.CardBean1012;
import kartina.card.bean.CardBean1013;
import kartina.card.bean.ClassDetailHeadBean;
import kartina.card.bean.TalkHeadBean;
import kartina.card.bean.TeacherHeadBean;
import kartina.card.view.CardView1000;
import kartina.card.view.CardView1012;
import kartina.card.view.CardView1013;
import kartina.card.view.TeacherHeadView;
import kartina.card.view.CheckMore;
import kartina.card.view.CardView1001;
import kartina.card.view.CardView1003;
import kartina.card.view.CardView1004;
import kartina.card.view.CardView1005;
import kartina.card.view.CardView1006;
import kartina.card.view.ClassDetailHeadView;
import kartina.card.view.CardView1008;
import kartina.card.view.TalkHeadView;
import kartina.card.view.CardView1010;

/**
 * Created by David on 2016/9/16.
 */

public enum CardViewType {
    CardViewType1000(R.layout.cardview_1000,0,1,CardView1000.class, CardBean1000.class),
    CardViewType1001(R.layout.cardview_1001, 1,1,CardView1001.class, CardBean1001.class),
    CardViewType1003(R.layout.cardview_1003, 3,1,CardView1003.class,CardView1003.class),
    CardViewType1007(R.layout.cardview_1007, 7,1, ClassDetailHeadView.class, ClassDetailHeadBean.class),
    CardViewType1004(R.layout.cardview_1004, 4,1,CardView1004.class, CardBean1004.class),
    CardViewType1006(R.layout.cardview_1006, 6,1,CardView1006.class, CardBean1006.class),
    CardViewType1008(R.layout.cardview_1008, 8,1,CardView1008.class, CardBean1008.class),
    CardViewType1005(R.layout.cardview_1005, 5,1,CardView1005.class, CardBean1005.class),
    CardViewType1009(R.layout.cardview_1009, 9,1, TalkHeadView.class, TalkHeadBean.class),
    CardViewType1010(R.layout.cardview_1010, 10,1,CardView1010.class, CardBean1010.class),
    CardViewType1002(R.layout.check_more,2 ,1, CheckMore.class,null),
    CardViewType1012(R.layout.cardview_1012, 12,1, CardView1012.class, CardBean1012.class),
    CardViewType1013(R.layout.cardview_1013, 13,1, CardView1013.class, CardBean1013.class),
    CardViewType1011(R.layout.cardview_1011, 11,1, TeacherHeadView.class, TeacherHeadBean.class);
    public int rid;
    public int type;
    public int PreNum;
    public Class viewClazz;
    public Class beanClazz;
    CardViewType(int rid,int type,int preNum,Class clazz,Class beanClazz){
        this.rid = rid;
        this.type = type;
        this.PreNum = preNum;
        this.viewClazz = clazz;
        this.beanClazz = beanClazz;
    }
   public static CardViewType getCardViewByType(int value){
        for (CardViewType cardViewType : CardViewType.values()) {
            if ((value % 1000) == cardViewType.type) {
                return cardViewType;
            }
        }
        return null;
    }
}
