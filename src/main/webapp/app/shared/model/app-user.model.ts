export interface IAppUser {
  id?: number;
  firstName?: string | null;
  lastName?: string | null;
  login?: string | null;
  password?: string | null;
  role?: string | null;
  email?: string | null;
}

export const defaultValue: Readonly<IAppUser> = {};
