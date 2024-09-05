import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAppSubject } from 'app/shared/model/app-subject.model';
import { getEntity, updateEntity, createEntity, reset } from './app-subject.reducer';

export const AppSubjectUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const appSubjectEntity = useAppSelector(state => state.appSubject.entity);
  const loading = useAppSelector(state => state.appSubject.loading);
  const updating = useAppSelector(state => state.appSubject.updating);
  const updateSuccess = useAppSelector(state => state.appSubject.updateSuccess);

  const handleClose = () => {
    navigate('/app-subject');
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
      ...appSubjectEntity,
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
          ...appSubjectEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="empEngagementApp.appSubject.home.createOrEditLabel" data-cy="AppSubjectCreateUpdateHeading">
            Create or edit a App Subject
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="app-subject-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Subject Code" id="app-subject-subjectCode" name="subjectCode" data-cy="subjectCode" type="text" />
              <ValidatedField label="Topic Code" id="app-subject-topicCode" name="topicCode" data-cy="topicCode" type="text" />
              <ValidatedField label="Status" id="app-subject-status" name="status" data-cy="status" type="text" />
              <ValidatedField
                label="Is Approval Required"
                id="app-subject-isApprovalRequired"
                name="isApprovalRequired"
                data-cy="isApprovalRequired"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Is Approved"
                id="app-subject-isApproved"
                name="isApproved"
                data-cy="isApproved"
                check
                type="checkbox"
              />
              <ValidatedField label="Details Json" id="app-subject-detailsJson" name="detailsJson" data-cy="detailsJson" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/app-subject" replace color="info">
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

export default AppSubjectUpdate;
