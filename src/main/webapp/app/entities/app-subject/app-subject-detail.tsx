import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './app-subject.reducer';

export const AppSubjectDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const appSubjectEntity = useAppSelector(state => state.appSubject.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="appSubjectDetailsHeading">App Subject</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{appSubjectEntity.id}</dd>
          <dt>
            <span id="subjectCode">Subject Code</span>
          </dt>
          <dd>{appSubjectEntity.subjectCode}</dd>
          <dt>
            <span id="topicCode">Topic Code</span>
          </dt>
          <dd>{appSubjectEntity.topicCode}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{appSubjectEntity.status}</dd>
          <dt>
            <span id="isApprovalRequired">Is Approval Required</span>
          </dt>
          <dd>{appSubjectEntity.isApprovalRequired ? 'true' : 'false'}</dd>
          <dt>
            <span id="isApproved">Is Approved</span>
          </dt>
          <dd>{appSubjectEntity.isApproved ? 'true' : 'false'}</dd>
          <dt>
            <span id="detailsJson">Details Json</span>
          </dt>
          <dd>{appSubjectEntity.detailsJson}</dd>
        </dl>
        <Button tag={Link} to="/app-subject" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/app-subject/${appSubjectEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AppSubjectDetail;
