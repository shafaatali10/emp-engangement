import appUser from 'app/entities/app-user/app-user.reducer';
import appLookup from 'app/entities/app-lookup/app-lookup.reducer';
import appTopicLookup from 'app/entities/app-topic-lookup/app-topic-lookup.reducer';
import appTopicType from 'app/entities/app-topic-type/app-topic-type.reducer';
import appSubjectType from 'app/entities/app-subject-type/app-subject-type.reducer';
import appSubject from 'app/entities/app-subject/app-subject.reducer';
import appSubjectConfig from 'app/entities/app-subject-config/app-subject-config.reducer';
import appUserGroup from 'app/entities/app-user-group/app-user-group.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  appUser,
  appLookup,
  appTopicLookup,
  appTopicType,
  appSubjectType,
  appSubject,
  appSubjectConfig,
  appUserGroup,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
