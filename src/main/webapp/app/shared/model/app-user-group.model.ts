export interface IAppUserGroup {
  id?: number;
  groupName?: string | null;
  email?: string | null;
  adminUser?: string | null;
}

export const defaultValue: Readonly<IAppUserGroup> = {};
