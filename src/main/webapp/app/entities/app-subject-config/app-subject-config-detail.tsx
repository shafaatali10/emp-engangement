import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './app-subject-config.reducer';

export const AppSubjectConfigDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const appSubjectConfigEntity = useAppSelector(state => state.appSubjectConfig.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="appSubjectConfigDetailsHeading">App Subject Config</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{appSubjectConfigEntity.id}</dd>
          <dt>
            <span id="subjectCode">Subject Code</span>
          </dt>
          <dd>{appSubjectConfigEntity.subjectCode}</dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{appSubjectConfigEntity.version}</dd>
          <dt>
            <span id="payload">Payload</span>
          </dt>
          <dd>{appSubjectConfigEntity.payload}</dd>
        </dl>
        <Button tag={Link} to="/app-subject-config" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/app-subject-config/${appSubjectConfigEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AppSubjectConfigDetail;
