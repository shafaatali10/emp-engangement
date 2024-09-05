export interface IAppSubject {
  id?: number;
  subjectCode?: string | null;
  topicCode?: string | null;
  status?: string | null;
  isApprovalRequired?: boolean | null;
  isApproved?: boolean | null;
  detailsJson?: string | null;
}

export const defaultValue: Readonly<IAppSubject> = {
  isApprovalRequired: false,
  isApproved: false,
};
