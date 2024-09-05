import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './app-topic-type.reducer';

export const AppTopicTypeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const appTopicTypeEntity = useAppSelector(state => state.appTopicType.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="appTopicTypeDetailsHeading">App Topic Type</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{appTopicTypeEntity.id}</dd>
          <dt>
            <span id="topicCode">Topic Code</span>
          </dt>
          <dd>{appTopicTypeEntity.topicCode}</dd>
          <dt>
            <span id="topicName">Topic Name</span>
          </dt>
          <dd>{appTopicTypeEntity.topicName}</dd>
          <dt>
            <span id="targetGroup">Target Group</span>
          </dt>
          <dd>{appTopicTypeEntity.targetGroup}</dd>
        </dl>
        <Button tag={Link} to="/app-topic-type" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/app-topic-type/${appTopicTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AppTopicTypeDetail;
