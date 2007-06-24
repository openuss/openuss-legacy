package org.openuss.web.braincontest;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;

/**
 * @author Sebastian Roekens
 */
@Bean(name = "views$secured$braincontest$braincontestwrong", scope = Scope.REQUEST)
@View
public class BrainContestWrongPage extends AbstractBrainContestPage {

}