import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './app-topic-lookup.reducer';

export const AppTopicLookupDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const appTopicLookupEntity = useAppSelector(state => state.appTopicLookup.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="appTopicLookupDetailsHeading">App Topic Lookup</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{appTopicLookupEntity.id}</dd>
          <dt>
            <span id="topicCode">Topic Code</span>
          </dt>
          <dd>{appTopicLookupEntity.topicCode}</dd>
          <dt>
            <span id="topicName">Topic Name</span>
          </dt>
          <dd>{appTopicLookupEntity.topicName}</dd>
          <dt>
            <span id="targetGroup">Target Group</span>
          </dt>
          <dd>{appTopicLookupEntity.targetGroup}</dd>
        </dl>
        <Button tag={Link} to="/app-topic-lookup" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/app-topic-lookup/${appTopicLookupEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AppTopicLookupDetail;
