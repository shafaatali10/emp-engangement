import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAppSubjectConfig } from 'app/shared/model/app-subject-config.model';
import { getEntity, updateEntity, createEntity, reset } from './app-subject-config.reducer';

export const AppSubjectConfigUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const appSubjectConfigEntity = useAppSelector(state => state.appSubjectConfig.entity);
  const loading = useAppSelector(state => state.appSubjectConfig.loading);
  const updating = useAppSelector(state => state.appSubjectConfig.updating);
  const updateSuccess = useAppSelector(state => state.appSubjectConfig.updateSuccess);

  const handleClose = () => {
    navigate('/app-subject-config');
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
    if (values.version !== undefined && typeof values.version !== 'number') {
      values.version = Number(values.version);
    }

    const entity = {
      ...appSubjectConfigEntity,
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
          ...appSubjectConfigEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="empEngagementApp.appSubjectConfig.home.createOrEditLabel" data-cy="AppSubjectConfigCreateUpdateHeading">
            Create or edit a App Subject Config
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
                <ValidatedField name="id" required readOnly id="app-subject-config-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Subject Code"
                id="app-subject-config-subjectCode"
                name="subjectCode"
                data-cy="subjectCode"
                type="text"
              />
              <ValidatedField label="Version" id="app-subject-config-version" name="version" data-cy="version" type="text" />
              <ValidatedField label="Payload" id="app-subject-config-payload" name="payload" data-cy="payload" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/app-subject-config" replace color="info">
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

export default AppSubjectConfigUpdate;
