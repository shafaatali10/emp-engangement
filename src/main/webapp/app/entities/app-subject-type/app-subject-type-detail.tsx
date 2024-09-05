import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './app-subject-type.reducer';

export const AppSubjectTypeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const appSubjectTypeEntity = useAppSelector(state => state.appSubjectType.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="appSubjectTypeDetailsHeading">App Subject Type</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{appSubjectTypeEntity.id}</dd>
          <dt>
            <span id="subjectCode">Subject Code</span>
          </dt>
          <dd>{appSubjectTypeEntity.subjectCode}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{appSubjectTypeEntity.description}</dd>
        </dl>
        <Button tag={Link} to="/app-subject-type" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/app-subject-type/${appSubjectTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AppSubjectTypeDetail;
