import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAppUserGroup } from 'app/shared/model/app-user-group.model';
import { getEntity, updateEntity, createEntity, reset } from './app-user-group.reducer';

export const AppUserGroupUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const appUserGroupEntity = useAppSelector(state => state.appUserGroup.entity);
  const loading = useAppSelector(state => state.appUserGroup.loading);
  const updating = useAppSelector(state => state.appUserGroup.updating);
  const updateSuccess = useAppSelector(state => state.appUserGroup.updateSuccess);

  const handleClose = () => {
    navigate('/app-user-group');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...appUserGroupEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...appUserGroupEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="empEngagementApp.appUserGroup.home.createOrEditLabel" data-cy="AppUserGroupCreateUpdateHeading">
            Create or edit a App User Group
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="app-user-group-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Group Name" id="app-user-group-groupName" name="groupName" data-cy="groupName" type="text" />
              <ValidatedField label="Email" id="app-user-group-email" name="email" data-cy="email" type="text" />
              <ValidatedField label="Admin User" id="app-user-group-adminUser" name="adminUser" data-cy="adminUser" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/app-user-group" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default AppUserGroupUpdate;
