import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './app-user-group.reducer';

export const AppUserGroupDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const appUserGroupEntity = useAppSelector(state => state.appUserGroup.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="appUserGroupDetailsHeading">App User Group</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{appUserGroupEntity.id}</dd>
          <dt>
            <span id="groupName">Group Name</span>
          </dt>
          <dd>{appUserGroupEntity.groupName}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{appUserGroupEntity.email}</dd>
          <dt>
            <span id="adminUser">Admin User</span>
          </dt>
          <dd>{appUserGroupEntity.adminUser}</dd>
        </dl>
        <Button tag={Link} to="/app-user-group" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/app-user-group/${appUserGroupEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AppUserGroupDetail;
